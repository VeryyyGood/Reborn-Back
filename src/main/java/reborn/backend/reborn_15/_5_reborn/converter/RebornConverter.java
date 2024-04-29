package reborn.backend.reborn_15._5_reborn.converter;

import lombok.NoArgsConstructor;
import reborn.backend.pet.domain.Pet;
import reborn.backend.reborn_15._5_reborn.domain.Reborn;
import reborn.backend.reborn_15._5_reborn.dto.RebornResponseDto.DetailRebornDto;
import reborn.backend.reborn_15._5_reborn.dto.RebornResponseDto.SimpleRebornDto;

@NoArgsConstructor
public class RebornConverter {

    public static Reborn toReborn(Pet pet, Integer date) {
        return Reborn.builder()
                .pet(pet)
                .date(date)
                .pat(false)
                .feed(false)
                .wash(false)
                .brush(false)
                .build();
    }

    public static SimpleRebornDto toSimpleRebornDto(Reborn reborn) {
        return SimpleRebornDto.builder()
                .petType(String.valueOf(reborn.getPet().getPetType()))
                .rebornType(String.valueOf(reborn.getRebornType()))
                .userNickName(reborn.getPet().getUser().getNickname())
                .petName(reborn.getPet().getPetName())
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
