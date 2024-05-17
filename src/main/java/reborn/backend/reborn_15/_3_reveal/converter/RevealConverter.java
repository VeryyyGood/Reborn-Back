package reborn.backend.reborn_15._3_reveal.converter;

import lombok.NoArgsConstructor;
import reborn.backend.pet.domain.Pet;
import reborn.backend.reborn_15._3_reveal.domain.Reveal;
import reborn.backend.reborn_15._3_reveal.dto.RevealResponseDto.DetailRevealDto;
import reborn.backend.reborn_15._3_reveal.dto.RevealResponseDto.SimpleRevealDto;

@NoArgsConstructor
public class RevealConverter {

    public static Reveal toReveal(Pet pet, Integer date) {
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
                .resultEmotion(String.valueOf(reveal.getResultEmotion()))
                .id(reveal.getId())
                .date(reveal.getDate())
                .build();
    }

    public static DetailRevealDto toDetailRevealDto(Reveal reveal) {
        return DetailRevealDto.builder()
                .diaryContent(reveal.getDiaryContent())
                .build();
    }

}
