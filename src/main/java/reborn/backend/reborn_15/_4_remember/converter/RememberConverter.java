package reborn.backend.reborn_15._4_remember.converter;

import lombok.NoArgsConstructor;
import reborn.backend.pet.domain.Pet;
import reborn.backend.reborn_15._4_remember.domain.Remember;
import reborn.backend.reborn_15._4_remember.dto.RememberRequestDto.RememberReqDto;
import reborn.backend.reborn_15._4_remember.dto.RememberResponseDto.DetailRememberDto;

@NoArgsConstructor
public class RememberConverter {

    public static Remember toRemember(RememberReqDto remember, Pet pet) {
        return Remember.builder()
                .pet(pet)
                .date(remember.getDate())
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

    public static DetailRememberDto toDetailRememberDto(Remember remember) {
        return DetailRememberDto.builder()
                .id(remember.getId())
                .content(remember.getContent())
                .rememberImage(remember.getRememberImage())
                .pat(remember.getPat())
                .feed(remember.getFeed())
                .walk(remember.getWalk())
                .snack(remember.getSnack())
                .clean_1(remember.getClean_1())
                .clean_2(remember.getClean_2())
                .clean_3(remember.getClean_3())
                .clean_4(remember.getClean_4())
                .clean_5(remember.getClean_5())
                .clean_6(remember.getClean_6())
                .build();
    }
}
