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
                .clean_1(false)
                .clean_2(false)
                .clean_3(false)
                .clean_4(false)
                .clean_5(false)
                .clean_6(false)
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
                .clean_1(remember.getClean_1())
                .clean_2(remember.getClean_2())
                .clean_3(remember.getClean_3())
                .clean_4(remember.getClean_4())
                .clean_5(remember.getClean_5())
                .clean_6(remember.getClean_6())
                .build();
    }
}
