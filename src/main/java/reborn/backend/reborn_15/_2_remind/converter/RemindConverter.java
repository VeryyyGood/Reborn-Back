package reborn.backend.reborn_15._2_remind.converter;

import lombok.NoArgsConstructor;
import reborn.backend.pet.domain.Pet;
import reborn.backend.reborn_15._2_remind.domain.Remind;
import reborn.backend.reborn_15._2_remind.dto.RemindResponseDto.DetailRemindDto;
import reborn.backend.reborn_15._2_remind.dto.RemindResponseDto.SimpleRemindDto;

@NoArgsConstructor
public class RemindConverter {

    public static Remind toRemind(Pet pet, Integer date) {
        return Remind.builder()
                .pet(pet)
                .date(date)
                .pat(false)
                .feed(false)
                .walk(false)
                .snack(false)
                .build();
    }

    public static SimpleRemindDto toSimpleRemindDto(Remind remind) {
        return SimpleRemindDto.builder()
                .answer(remind.getAnswer())
                .date(remind.getDate())
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
