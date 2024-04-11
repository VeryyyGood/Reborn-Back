package reborn.backend.reborn_15._2_remind.converter;

import lombok.NoArgsConstructor;
import reborn.backend.pet.domain.Pet;
import reborn.backend.reborn_15._2_remind.domain.Remind;
import reborn.backend.reborn_15._2_remind.dto.RemindRequestDto;
import reborn.backend.reborn_15._2_remind.dto.RemindRequestDto.RemindReqDto;
import reborn.backend.reborn_15._2_remind.dto.RemindResponseDto;
import reborn.backend.reborn_15._2_remind.dto.RemindResponseDto.DetailRemindDto;

@NoArgsConstructor
public class RemindConverter {

    public static Remind toRemind(RemindReqDto remind, Pet pet) {
        return Remind.builder()
                .pet(pet)
                .date(remind.getDate())
                .pat(false)
                .feed(false)
                .walk(false)
                .snack(false)
                .build();
    }

    public static DetailRemindDto toDetailRemindDto(Remind remind) {
        return DetailRemindDto.builder()
                .id(remind.getId())
                .answer(remind.getAnswer())
                .date(remind.getDate())
                .pat(remind.getPat())
                .feed(remind.getFeed())
                .walk(remind.getWalk())
                .snack(remind.getSnack())
                .build();
    }
}