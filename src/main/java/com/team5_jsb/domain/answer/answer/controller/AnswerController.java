package com.team5_jsb.domain.answer.answer.controller;

import com.team5_jsb.domain.answer.answer.dto.AnswerCreateDto;
import com.team5_jsb.domain.answer.answer.dto.AnswerUpdateDto;
import com.team5_jsb.domain.answer.answer.entity.Answer;
import com.team5_jsb.domain.answer.answer.service.AnswerService;
import com.team5_jsb.domain.question.question.entity.Question;
import com.team5_jsb.domain.question.question.dto.QuestionResponseDTO;
import com.team5_jsb.domain.question.question.service.QuestionService;
import com.team5_jsb.domain.user.user.dto.CustomUserDetails;
import com.team5_jsb.domain.user.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {
    private final AnswerService answerService;
    private final QuestionService questionService;

    @PostMapping("/create/{id}")
    public String createAnswer(Model model,
                               @PathVariable("id") long id,
                               @Valid AnswerCreateDto answerCreateDto, BindingResult bindingResult,
                               @AuthenticationPrincipal CustomUserDetails userDetails,
                               RedirectAttributes redirectAttributes) {
        // 질문은 DTO로 재조립하지 말고 실제 엔티티 로딩
        Question question = questionService.getQuestionEntity(id);

        if (bindingResult.hasErrors()) {
            // 템플릿이 필요로 하는 모델 값 재주입
            model.addAttribute("question", questionService.getQuestion(id, false)); // 조회수 증가 방지
            // 답변 페이징도 넣기
            model.addAttribute("answerPaging", answerService.getAnswers(question, 0));
            return "question_detail";
        }

        // 현재 로그인 사용자 (엔티티)
        User author = userDetails.getUser();

        //  author 포함 버전 호출
        answerService.create(question, author, answerCreateDto.getContent());
        redirectAttributes.addFlashAttribute("justAnswered", true);
        return "redirect:/question/detail/" + id;
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