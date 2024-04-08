package reborn.backend.reborn_15._4_remember.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "remember", description = "remember 관련 api 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/reborn/{pet-id}/remember")
public class RememberController {
}
