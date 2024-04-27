package reborn.backend.pet.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reborn.backend.pet.service.PetService;
import reborn.backend.user.service.UserService;


@Tag(name = "반려동물 정보", description = "반려동물 정보 관련 api 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/pet")
public class PetController {

    private final UserService userService;
    private final PetService petService;


}
