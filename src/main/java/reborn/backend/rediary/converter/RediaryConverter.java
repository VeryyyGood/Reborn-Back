package reborn.backend.rediary.converter;

import lombok.NoArgsConstructor;
import reborn.backend.rediary.domain.EmotionStatus;
import reborn.backend.rediary.domain.Rediary;
import reborn.backend.rediary.dto.RediaryRequestDto.RediaryReqDto;
import reborn.backend.rediary.dto.RediaryResponseDto.DetailRediaryDto;
import reborn.backend.rediary.dto.RediaryResponseDto.SimpleRediaryDto;

@NoArgsConstructor
public class RediaryConverter {

    public static Rediary toRediary(RediaryReqDto reqDto) {

        return Rediary.builder()
                .rediaryTitle(reqDto.getRediaryTitle())
                .rediaryContents(reqDto.getRediaryContents())
                .emotionStatus(EmotionStatus.valueOf(reqDto.getEmotionStatus()))
                .build();
    }

    public static SimpleRediaryDto toSimpleRediaryDto(Rediary rediary) {
        return SimpleRediaryDto.builder()
                .rediaryId(rediary.getRediaryId())
                .rediaryTitle(rediary.getRediaryTitle())
                .rediaryContents(rediary.getRediaryContents())
                .emotionStatus(String.valueOf(rediary.getEmotionStatus()))
                .rediaryCreatedAt(rediary.getCreatedAt())
                .build();
    }

    public static DetailRediaryDto toDetailRediaryDto(Rediary rediary) {
        return DetailRediaryDto.builder()
                .rediaryId(rediary.getRediaryId())
                .rediaryTitle(rediary.getRediaryTitle())
                .rediaryContents(rediary.getRediaryContents())
                .emotionStatus(String.valueOf(rediary.getEmotionStatus()))
                .rediaryCreatedAt(rediary.getCreatedAt())
                .build();
    }
}
