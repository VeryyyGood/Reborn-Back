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
                .question(remind.getQuestion())
                .answer(remind.getAnswer())
                .date(remind.getDate())
                .petName(remind.getPet().getPetName())
                .petType(String.valueOf(remind.getPet().getPetType()))
                .build();
    }

    public static DetailRemindDto toDetailRemindDto(Remind remind) {
        return DetailRemindDto.builder()
                .id(remind.getId())
                .question(remind.getQuestion())
                .answer(remind.getAnswer())
                .date(remind.getDate())
                .build();
    }
}
