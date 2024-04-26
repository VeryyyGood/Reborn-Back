package reborn.backend.reborn_15._3_reveal.converter;

import lombok.NoArgsConstructor;
import reborn.backend.pet.domain.Pet;
import reborn.backend.reborn_15._3_reveal.domain.Reveal;
import reborn.backend.reborn_15._3_reveal.dto.RevealRequestDto.RevealReqDto;
import reborn.backend.reborn_15._3_reveal.dto.RevealResponseDto;
import reborn.backend.reborn_15._3_reveal.dto.RevealResponseDto.DetailRevealDto;
import reborn.backend.reborn_15._3_reveal.dto.RevealResponseDto.SimpleRevealDto;

@NoArgsConstructor
public class RevealConverter {

    public static Reveal toReveal(RevealReqDto reveal, Pet pet, Integer date) {
        return Reveal.builder()
                .pet(pet)
                .date(date)
                .pat(false)
                .feed(false)
                .walk(false)
                .snack(false)
                .build();
    }

    public static SimpleRevealDto toSimpleRevealDto(Reveal reveal) {
        return SimpleRevealDto.builder()
                .pickEmotion(String.valueOf(reveal.getPickEmotion()))
                .diaryContent(reveal.getDiaryContent())
                .date(reveal.getDate())
                .build();
    }

    public static DetailRevealDto toDetailRevealDto(Reveal reveal) {
        return DetailRevealDto.builder()
                .id(reveal.getId())
                .diaryContent(reveal.getDiaryContent())
                .date(reveal.getDate())
                .pickEmotion(String.valueOf(reveal.getPickEmotion()))
                .resultEmotion(String.valueOf(reveal.getResultEmotion()))
                .pat(reveal.getPat())
                .feed(reveal.getFeed())
                .walk(reveal.getWalk())
                .snack(reveal.getSnack())
                .build();
    }

}
