package com.team5_jsb.domain.question.question.controller;

import com.team5_jsb.domain.answer.answer.dto.AnswerCreateDto;
import com.team5_jsb.domain.answer.answer.entity.Answer;
import com.team5_jsb.domain.answer.answer.service.AnswerService;
import com.team5_jsb.domain.question.question.dto.QuestionCreateDTO;
import com.team5_jsb.domain.question.question.dto.QuestionResponseDTO;
import com.team5_jsb.domain.question.question.dto.QuestionUpdateDto;
import com.team5_jsb.domain.question.question.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.team5_jsb.domain.question.question.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
        Page<QuestionResponseDTO> paging = this.questionService.getList(page, kw);
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
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
    public String detail(@PathVariable("id") Long id,
                         Model model,
                         @RequestParam(value = "page", defaultValue = "0") int page,
                         @RequestParam(value = "answerPage", defaultValue = "0") int answerPage,
                         AnswerCreateDto answerDto) {
        // 질문 정보 조회 (DTO)
        QuestionResponseDTO questionDto = this.questionService.getQuestion(id);

        // 답변 페이징
        Question questionEntity = this.questionService.getQuestionEntity(id);
        Page<Answer> answerPaging = this.answerService.getAnswers(questionEntity, answerPage);

        // 모델에 데이터 추가
        model.addAttribute("question", questionDto);
        model.addAttribute("answerPaging", answerPaging);
        model.addAttribute("answerCreateDto", answerDto);  // 답변 작성 폼을 위한 DTO 추가

        return "question_detail";
    }
      
    @GetMapping("/modify/{id}")
    public String modifyForm(@PathVariable Long id, Model model) {
        QuestionResponseDTO question = questionService.getQuestion(id);

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
