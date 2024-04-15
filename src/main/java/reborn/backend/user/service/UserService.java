package reborn.backend.user.service;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import reborn.backend.global.api_payload.ErrorCode;
import reborn.backend.global.exception.GeneralException;
import reborn.backend.global.s3.AmazonS3Manager;
import reborn.backend.user.domain.User;
import reborn.backend.user.jwt.JwtDto;
import reborn.backend.user.jwt.JwtTokenUtils;
import reborn.backend.user.jwt.RefreshToken;
import reborn.backend.user.repository.RefreshTokenRepository;
import reborn.backend.user.repository.UserRepository;
import java.io.IOException;
import java.util.Objects;

import static org.apache.logging.log4j.util.Strings.isEmpty;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private final PasswordEncoder passwordEncoder;
    private final JpaUserDetailsManager manager;
    private final JwtTokenUtils jwtTokenUtils;
    private final AmazonS3Manager amazonS3Manager;

    // 로그인 - OAuth2SuccessHandler

    // 로그아웃
    public void logout(HttpServletRequest request) {
        // 1. access token 찾아오기
        String accessToken = request.getHeader("Authorization").split(" ")[1];

        // 2. 리프레시 토큰을 username으로 찾아 삭제
        String username = jwtTokenUtils.parseClaims(accessToken).getSubject();
        log.info("access token에서 추출한 username : {}", username);
        if (refreshTokenRepository.existsById(username)) {
            refreshTokenRepository.deleteById(username);
            log.info("DB에서 리프레시 토큰 삭제 완료");
        } else {
            throw GeneralException.of(ErrorCode.WRONG_REFRESH_TOKEN);
        }
    }

    // access, refresh 토큰 재발급
    public JwtDto reissue(HttpServletRequest request) {
        // 1. Request에서 Refresh Token 추출
        String refreshTokenValue = request.getHeader("Authorization").split(" ")[1];

        // 2. DB에서 해당 Refresh Token을 찾음
        RefreshToken refreshToken = refreshTokenRepository.findByRefreshToken(refreshTokenValue)
                .orElseThrow(() -> new GeneralException(ErrorCode.WRONG_REFRESH_TOKEN));
        log.info("찾은 refresh token : {}", refreshToken);

        // 3. Refresh Token의 유효기간 확인 (생략)

        // 4. Refresh Token을 발급한 사용자 정보 로드
        UserDetails userDetails = manager.loadUserByUsername(refreshToken.getId());
        log.info("refresh token에서 추출한 username : {}", refreshToken.getId());

        // 5. 새로운 Access Token 및 Refresh Token 생성
        JwtDto jwt = jwtTokenUtils.generateToken(userDetails);
        log.info("reissue: refresh token 재발급 완료");

        // 6. Refresh Token 정보 업데이트 및 DB에 저장
        refreshToken.updateRefreshToken(jwt.getRefreshToken());
        Claims refreshTokenClaims = jwtTokenUtils.parseClaims(jwt.getRefreshToken());
        Long validPeriod = refreshTokenClaims.getExpiration().toInstant().getEpochSecond()
                - refreshTokenClaims.getIssuedAt().toInstant().getEpochSecond();
        refreshToken.updateTtl(validPeriod);
        refreshTokenRepository.save(refreshToken);
        log.info("accessToken: {}", jwt.getAccessToken());
        log.info("refreshToken: {} ", jwt.getRefreshToken());

        // 7. DB에 새로운 리프레시 토큰이 정상적으로 저장되었는지 확인
        if (!refreshTokenRepository.existsById(refreshToken.getId())) {
            throw GeneralException.of(ErrorCode.WRONG_REFRESH_TOKEN);
        }

        return jwt;
    }

    // 회원 탈퇴
    public void deleteUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new GeneralException(ErrorCode.USER_NOT_FOUND));
        if (refreshTokenRepository.existsById(username)) {
            refreshTokenRepository.deleteById(username);
            log.info("DB에서 리프레시 토큰 삭제 완료");
        }
        userRepository.delete(user);
        log.info("{} 회원 탈퇴 완료", username);
    }

    @Transactional
    public void createProfileImage(String dirName, MultipartFile file, User user) throws IOException {
        String uploadFileUrl = null;

        if (file != null) {
            String contentType = file.getContentType();
            if (ObjectUtils.isEmpty(contentType)) {
                throw GeneralException.of(ErrorCode.INVALID_FILE_CONTENT_TYPE);
            }

            MediaType mediaType = amazonS3Manager.contentType(Objects.requireNonNull(file.getOriginalFilename()));
            if (mediaType == null || !(mediaType.equals(MediaType.IMAGE_PNG) || mediaType.equals(MediaType.IMAGE_JPEG))) {
                throw GeneralException.of(ErrorCode.MISMATCH_IMAGE_FILE);
            }

            // 이전 프로필 이미지가 존재하는지 확인
            if (!isEmpty(user.getProfileImage())) {
                // 기존 프로필 이미지를 S3에서 삭제
                String previousFilePath = user.getProfileImage();
                amazonS3Manager.delete(previousFilePath); // S3에서 삭제
            }

            java.io.File uploadFile = amazonS3Manager.convert(file)
                    .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다."));

            String fileName = dirName + amazonS3Manager.generateFileName(file);
            uploadFileUrl = amazonS3Manager.putS3(uploadFile, fileName);

            user.setProfileImage(uploadFileUrl); // 새로운 사진 url 저장
            userRepository.save(user);
        }
    }

    @Transactional
    public void createBackgroundImage(String dirName, MultipartFile file, User user) throws IOException {
        String uploadFileUrl = null;

        if (file != null) {
            String contentType = file.getContentType();
            if (ObjectUtils.isEmpty(contentType)) {
                throw GeneralException.of(ErrorCode.INVALID_FILE_CONTENT_TYPE);
            }

            MediaType mediaType = amazonS3Manager.contentType(Objects.requireNonNull(file.getOriginalFilename()));
            if (mediaType == null || !(mediaType.equals(MediaType.IMAGE_PNG) || mediaType.equals(MediaType.IMAGE_JPEG))) {
                throw GeneralException.of(ErrorCode.MISMATCH_IMAGE_FILE);
            }

            // 이전 프로필 이미지가 존재하는지 확인
            if (!isEmpty(user.getBackgroundImage())) {
                // 기존 프로필 이미지를 S3에서 삭제
                String previousFilePath = user.getBackgroundImage();
                amazonS3Manager.delete(previousFilePath); // S3에서 삭제
            }

            java.io.File uploadFile = amazonS3Manager.convert(file)
                    .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다."));

            String fileName = dirName + amazonS3Manager.generateFileName(file);
            uploadFileUrl = amazonS3Manager.putS3(uploadFile, fileName);

            user.setBackgroundImage(uploadFileUrl); // 새로운 사진 url 저장
            userRepository.save(user);
        }
    }

    @Transactional
    public String showProfileImage(User user){
        return user.getProfileImage();
    }

    @Transactional
    public String showBackgroundImage(User user){
        return user.getBackgroundImage();
    }

    // username으로 User찾기
    public User findUserByUserName(String userName){
        return userRepository.findByUsername(userName)
                .orElseThrow(() -> new GeneralException(ErrorCode.USER_NOT_FOUND));
    }

}
