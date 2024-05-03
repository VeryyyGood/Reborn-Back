package reborn.backend.reborn_15._4_remember.converter;

import lombok.NoArgsConstructor;
import reborn.backend.pet.domain.Pet;
import reborn.backend.reborn_15._4_remember.domain.Remember;
import reborn.backend.reborn_15._4_remember.dto.RememberResponseDto.DetailRememberDto;
import reborn.backend.reborn_15._4_remember.dto.RememberResponseDto.SimpleRememberDto;

@NoArgsConstructor
public class RememberConverter {

    public static Remember toRemember(Pet pet, Integer date) {
        return Remember.builder()
                .pet(pet)
                .date(date)
                .pat(false)
                .feed(false)
                .walk(false)
                .snack(false)
                .clean(false)
                .build();
    }

    public static SimpleRememberDto toSimpleRememberDto(Remember remember) {
        return SimpleRememberDto.builder()
                .content(remember.getContent())
                .rememberImage(remember.getRememberImage())
                .date(remember.getDate())
                .build();
    }

    public static DetailRememberDto toDetailRememberDto(Remember remember) {
        return DetailRememberDto.builder()
                .id(remember.getId())
                .content(remember.getContent())
                .date(remember.getDate())
                .rememberImage(remember.getRememberImage())
                .build();
    }
}
