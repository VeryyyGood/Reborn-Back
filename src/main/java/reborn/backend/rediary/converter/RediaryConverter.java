package reborn.backend.rediary.converter;

import lombok.NoArgsConstructor;
import reborn.backend.rediary.domain.EmotionStatus;
import reborn.backend.rediary.domain.Rediary;
import reborn.backend.rediary.dto.RediaryRequestDto.RediaryReqDto;
import reborn.backend.rediary.dto.RediaryResponseDto.DetailRediaryDto;
import reborn.backend.rediary.dto.RediaryResponseDto.SimpleRediaryDto;
import reborn.backend.user.domain.User;

@NoArgsConstructor
public class RediaryConverter {

    public static Rediary toRediary(RediaryReqDto rediary, User user) {
        return Rediary.builder()
                .rediaryWriter(user.getUsername()) // 작성자
                .rediaryTitle(rediary.getRediaryTitle())
                .rediaryContents(rediary.getRediaryContents())
                .emotionStatus(EmotionStatus.valueOf(rediary.getEmotionStatus()))
                .build();
    }

    public static SimpleRediaryDto toSimpleRediaryDto(Rediary rediary) {
        return SimpleRediaryDto.builder()
                .rediaryId(rediary.getRediaryId())
                .rediaryWriter(rediary.getRediaryWriter())
                .rediaryTitle(rediary.getRediaryTitle())
                .rediaryContents(rediary.getRediaryContents())
                .emotionStatus(String.valueOf(rediary.getEmotionStatus()))
                .rediaryCreatedAt(rediary.getRediaryCreatedAt())
                .build();
    }

    public static DetailRediaryDto toDetailRediaryDto(Rediary rediary) {
        return DetailRediaryDto.builder()
                .rediaryId(rediary.getRediaryId())
                .rediaryWriter(rediary.getRediaryWriter())
                .rediaryTitle(rediary.getRediaryTitle())
                .rediaryContents(rediary.getRediaryContents())
                .emotionStatus(String.valueOf(rediary.getEmotionStatus()))
                .rediaryCreatedAt(rediary.getRediaryCreatedAt())
                .build();
    }
}
