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
                .id(remember.getId())
                .title(remember.getTitle())
                .rememberImage(remember.getRememberImage())
                .date(remember.getDate())
                .build();
    }

    public static DetailRememberDto toDetailRememberDto(Remember remember) {
        return DetailRememberDto.builder()
                .title(remember.getTitle())
                .content(remember.getContent())
                .imageDate(remember.getImageDate())
                .rememberImage(remember.getRememberImage())
                .build();
    }
}
