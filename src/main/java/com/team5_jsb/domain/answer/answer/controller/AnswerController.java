package com.team5_jsb.domain.answer.answer.controller;

import com.team5_jsb.domain.answer.answer.dto.AnswerCreateDto;
import com.team5_jsb.domain.answer.answer.dto.AnswerUpdateDto;
import com.team5_jsb.domain.answer.answer.entity.Answer;
import com.team5_jsb.domain.answer.answer.service.AnswerService;
import com.team5_jsb.domain.question.question.entity.Question;
import com.team5_jsb.domain.question.question.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {
    private final AnswerService answerService;
    private final QuestionService questionService;

    @PostMapping("/create/{id}")
    public String createAnswer(Model model,
                               @PathVariable("id") long id,
                               @Valid AnswerCreateDto answerCreateDto, BindingResult bindingResult) {
        Question question = questionService.getQuestion(id);

        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "question_detail";
        }

        answerService.create(question, answerCreateDto.getContent());
        return String.format("redirect:/question/detail/%s", id);
    }

    @GetMapping("/delete/{id}")
    public String answerDelete(@PathVariable("id") long id) {
        Answer answer = answerService.getAnswer(id);
        answerService.delete(answer);
        return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
    }

    @GetMapping("/modify/{id}")
    public String answerModify(@PathVariable("id") long id, Model model) {
        Answer answer = answerService.getAnswer(id);
        AnswerUpdateDto answerUpdateDto = new AnswerUpdateDto();
        answerUpdateDto.setContent(answer.getContent());
        model.addAttribute("answerUpdateDto", answerUpdateDto);
        model.addAttribute("id", id);
        return "answer_form";
    }

    @PostMapping("/modify/{id}")
    public String answerModify(@Valid AnswerUpdateDto answerUpdateDto, BindingResult bindingResult,
                               @PathVariable("id") long id) {
        Answer answer = answerService.getAnswer(id);
        answerService.update(answer, answerUpdateDto.getContent());
        return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
    }

}