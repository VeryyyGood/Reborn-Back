package reborn.backend.reborn_15._2_remind.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "remind", description = "remind 관련 api 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/reborn/{pet-id}/remind")
public class RemindController {
}
