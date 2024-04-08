package reborn.backend.rediary.converter;

import lombok.NoArgsConstructor;
import reborn.backend.global.entity.PickEmotion;
import reborn.backend.rediary.domain.Rediary;
import reborn.backend.global.entity.ResultEmotion;
import reborn.backend.rediary.dto.RediaryRequestDto.RediaryReqDto;
import reborn.backend.rediary.dto.RediaryResponseDto.DetailRediaryDto;
import reborn.backend.user.domain.User;

@NoArgsConstructor
public class RediaryConverter {

    public static Rediary toRediary(RediaryReqDto rediary, User user) {
        return Rediary.builder()
                .rediaryWriter(user.getUsername()) // 작성자
                .rediaryTitle(rediary.getRediaryTitle())
                .rediaryContent(rediary.getRediaryContent())
                .pickEmotion(PickEmotion.valueOf(rediary.getPickEmotion()))
                .resultEmotion(ResultEmotion.valueOf(rediary.getResultEmotion()))
                .build();
    }

    public static DetailRediaryDto toDetailRediaryDto(Rediary rediary) {
        return DetailRediaryDto.builder()
                .rediaryId(rediary.getRediaryId())
                .rediaryWriter(rediary.getRediaryWriter())
                .rediaryTitle(rediary.getRediaryTitle())
                .rediaryContent(rediary.getRediaryContent())
                .pickEmotion(String.valueOf(rediary.getPickEmotion()))
                .resultEmotion(String.valueOf(rediary.getResultEmotion()))
                .rediaryCreatedAt(rediary.getRediaryCreatedAt())
                .build();
    }
}
