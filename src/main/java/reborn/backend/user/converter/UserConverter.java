package reborn.backend.user.converter;

import lombok.NoArgsConstructor;
import reborn.backend.board.domain.Board;
import reborn.backend.board.dto.BoardResponseDto;
import reborn.backend.user.domain.User;
import reborn.backend.user.dto.JwtDto;
import reborn.backend.user.dto.UserRequestDto;
import reborn.backend.user.dto.UserResponseDto;

@NoArgsConstructor
public class UserConverter {
    public static User saveUser(UserRequestDto.UserReqDto userReqDto) {
        return User.builder()
                .email(userReqDto.getEmail())
                .username(userReqDto.getUsername())
                .provider(userReqDto.getProvider())
                .nickname(userReqDto.getNickname())
                .contentPetId(null)
                .profileImage(null)
                .backgroundImage(null)
                .password("password")
                .providerId("providerId")
                .build();
    }

    public static JwtDto jwtDto(String access, String refresh) {
        return JwtDto.builder()
                .accessToken(access)
                .refreshToken(refresh)
                .build();
    }

    public static UserResponseDto.UserInfoResDto infoDto(User user) {
        return UserResponseDto.UserInfoResDto.builder()
                .since(user.getCreatedAt())
                .email(user.getEmail())
                .build();
    }
}