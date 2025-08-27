package com.team5_jsb.domain.question.question.controller;

import com.team5_jsb.domain.question.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import com.team5_jsb.domain.question.question.entity.Question;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/question")
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "kw", defaultValue = "") String kw) {
        List<Question> questionList = this.questionService.getList(kw);
        model.addAttribute("questionList", questionList);
        return "question/question_list";
    }
}
