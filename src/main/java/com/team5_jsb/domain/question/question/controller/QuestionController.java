package com.team5_jsb.domain.question.question.controller;

import com.team5_jsb.domain.answer.answer.entity.Answer;
import com.team5_jsb.domain.answer.answer.service.AnswerService;
import com.team5_jsb.domain.question.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import com.team5_jsb.domain.question.question.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/question")
public class QuestionController {
    private final QuestionService questionService;
    private final AnswerService answerService;

    @GetMapping("/list")
    public String list(Model model,
                       @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "kw", defaultValue = "") String kw) {
        Page<Question> paging = this.questionService.getList(page, kw);
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        return "question/question_list";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Long id,
                         @RequestParam(value = "page", defaultValue = "0") int page) {
        // 질문 조회 및 조회수 증가
        Question question = this.questionService.getQuestion(id);

        // 답변 목록 페이징 처리
        Page<Answer> answerPaging = this.answerService.getAnswers(question, page);

        model.addAttribute("question", question);
        model.addAttribute("answerPaging", answerPaging);
        return "question/question_detail";
    }
}
