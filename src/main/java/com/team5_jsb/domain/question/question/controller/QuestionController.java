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
      
      
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        questionService.deleteQuestion(id);

        return "redirect:/question/list";
      
    }
}
