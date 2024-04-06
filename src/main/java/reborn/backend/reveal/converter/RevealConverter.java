package reborn.backend.reveal.converter;

import lombok.NoArgsConstructor;
import reborn.backend.global.entity.PickEmotion;
import reborn.backend.global.entity.ResultEmotion;
import reborn.backend.reveal.domain.Reveal;
import reborn.backend.reveal.dto.RevealRequestDto.RevealReqDto;
import reborn.backend.reveal.dto.RevealResponseDto.DetailRevealDto;
import reborn.backend.user.domain.User;

@NoArgsConstructor
public class RevealConverter {

    public static Reveal toReveal(RevealReqDto reveal, User user) {
        return Reveal.builder()
                .revealWriter(user.getUsername())
                .revealContents(reveal.getRevealContents())
                .revealCreatedAt(reveal.getRevealCreatedAt())
                .pickEmotion(PickEmotion.valueOf(reveal.getPickEmotion()))
                .resultEmotion(ResultEmotion.valueOf(reveal.getResultEmotion()))
                .build();
    }

    public static DetailRevealDto toDetailRevealDto(Reveal remind) {
        return DetailRevealDto.builder()
                .revealId(remind.getRevealId())
                .revealWriter(remind.getRevealWriter())
                .revealContents(remind.getRevealContents())
                .pickEmotion(String.valueOf(remind.getPickEmotion()))
                .resultEmotion(String.valueOf(remind.getResultEmotion()))
                .revealCreatedAt(remind.getRevealCreatedAt())
                .build();
    }
}
