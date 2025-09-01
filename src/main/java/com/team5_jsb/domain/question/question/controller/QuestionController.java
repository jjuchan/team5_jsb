package com.team5_jsb.domain.question.question.controller;

import com.team5_jsb.domain.answer.answer.dto.AnswerCreateDto;
import com.team5_jsb.domain.question.question.dto.QuestionCreateDTO;
import com.team5_jsb.domain.question.question.dto.QuestionUpdateDto;
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

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/question")
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping("/list")
    public String list(Model model) {
        List<Question> questionList = questionService.getList();

        model.addAttribute("questionList", questionList);

        return "question_list";
    }

    @GetMapping("/create")
    public String create(QuestionCreateDTO questionCreateDTO) {
        return "question_form";
    }

    @PostMapping("/create")
    public String createQuestion(@Valid QuestionCreateDTO questionCreateDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        questionService.create(questionCreateDTO);
        return "redirect:/question/list";
    }


    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, Model model, AnswerCreateDto answerDto) {
        Question question = questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }

    @GetMapping("/modify/{id}")
    public String modifyForm(@PathVariable Long id, Model model) {
        Question question = questionService.getQuestion(id);

        QuestionUpdateDto dto = new QuestionUpdateDto();
        dto.setSubject(question.getSubject());
        dto.setContent(question.getContent());

        model.addAttribute("questionUpdateDto", dto);
        model.addAttribute("questionId", id);
        return "question_modify_form";
    }


    @PostMapping("/modify/{id}")
    public String questionModify(@Valid QuestionUpdateDto questionUpdateDto, BindingResult bindingResult, @PathVariable("id") Long id) {
        if (bindingResult.hasErrors()) {
            return "question_modify_form";
        }
        questionService.modify(questionUpdateDto, id);
        return "redirect:/question/detail/" + id;

    }
}
