package reborn.backend.rediary.converter;

import lombok.NoArgsConstructor;
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
                .rediaryCreatedAt(reqDto.getRediaryCreatedAt())
                .build();
    }

    public static SimpleRediaryDto toSimpleRediaryDto(Rediary rediary) {
        return SimpleRediaryDto.builder()
                .id(rediary.getId())
                .rediaryTitle(rediary.getRediaryTitle())
                .rediaryContents(rediary.getRediaryContents())
                .rediaryCreatedAt(rediary.getCreatedAt())
                .build();
    }

    public static DetailRediaryDto toDetailRediaryDto(Rediary rediary) {
        return DetailRediaryDto.builder()
                .id(rediary.getId())
                .rediaryTitle(rediary.getRediaryTitle())
                .rediaryContents(rediary.getRediaryContents())
                .rediaryCreatedAt(rediary.getCreatedAt())
                .build();
    }
}
