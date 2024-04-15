package reborn.backend.reborn_15._5_reborn.converter;

import lombok.NoArgsConstructor;
import reborn.backend.pet.domain.Pet;
import reborn.backend.reborn_15._5_reborn.domain.Reborn;
import reborn.backend.reborn_15._5_reborn.dto.RebornRequestDto.RebornReqDto;
import reborn.backend.reborn_15._5_reborn.dto.RebornResponseDto.DetailRebornDto;

@NoArgsConstructor
public class RebornConverter {

    public static Reborn toReborn(RebornReqDto reborn, Pet pet, Integer date) {
        return Reborn.builder()
                .pet(pet)
                .date(date)
                .pat(false)
                .feed(false)
                .wash(false)
                .brush(false)
                .build();
    }

    public static DetailRebornDto toDetailRebornDto(Reborn reborn) {
        return DetailRebornDto.builder()
                .id(reborn.getId())
                .rebornContent(reborn.getRebornContent())
                .date(reborn.getDate())
                .rebornType(String.valueOf(reborn.getRebornType()))
                .pat(reborn.getPat())
                .feed(reborn.getFeed())
                .wash(reborn.getWash())
                .brush(reborn.getBrush())
                .build();
    }
}
