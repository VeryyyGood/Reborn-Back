package reborn.backend.user.converter;

import lombok.NoArgsConstructor;
import reborn.backend.user.domain.User;
import reborn.backend.user.dto.JwtDto;
import reborn.backend.user.dto.UserRequestDto;

@NoArgsConstructor
public class UserConverter {
    public static User saveUser(UserRequestDto.UserReqDto userReqDto) {
        return User.builder()
                .email(userReqDto.getEmail())
                .username(userReqDto.getUsername())
                .provider(userReqDto.getProvider())
                .nickname(userReqDto.getNickname())
                .profileImage(null)
                .backgroundImage(null)
                .password("password")
                .providerId("naver")
                .build();
    }

    public static JwtDto jwtDto(String access, String refresh) {
        return JwtDto.builder()
                .accessToken(access)
                .refreshToken(refresh)
                .build();
    }
}