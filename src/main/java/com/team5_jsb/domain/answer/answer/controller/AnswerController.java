package com.team5_jsb.domain.answer.answer.controller;

import com.team5_jsb.domain.answer.answer.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class AnswerController {
    private final AnswerService answerService;

    @GetMapping("/")
    public String test() {
        return "test";
    }
}
