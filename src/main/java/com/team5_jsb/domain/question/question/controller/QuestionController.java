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
    public String list(Model model, @RequestParam(value = "kw", defaultValue = "") String kw) {
        List<Question> questionList = this.questionService.getList(kw);
        model.addAttribute("questionList", questionList);
        return "question/question_list";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Long id) {
        // 질문 조회 및 조회수 증가
        Question question = this.questionService.getQuestion(id);
        
        // 답변 목록 조회 (페이징 없이 전체 조회)
        List<Answer> answerList = this.answerService.getAnswers(question);
        
        model.addAttribute("question", question);
        model.addAttribute("answerList", answerList);
        return "question/question_detail";
    }
}
