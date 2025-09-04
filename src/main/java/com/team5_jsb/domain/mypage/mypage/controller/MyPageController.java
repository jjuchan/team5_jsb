package com.team5_jsb.domain.mypage.mypage.controller;

import com.team5_jsb.domain.answer.answer.entity.Answer;
import com.team5_jsb.domain.answer.answer.service.AnswerService;
import com.team5_jsb.domain.question.question.entity.Question;
import com.team5_jsb.domain.question.question.repository.QuestionRepository;
import com.team5_jsb.domain.question.question.service.QuestionService;
import com.team5_jsb.domain.user.user.dto.CustomOidcUser;
import com.team5_jsb.domain.user.user.dto.CustomUserDetails;
import com.team5_jsb.domain.user.user.dto.UserProfileDto;
import com.team5_jsb.domain.user.user.entity.User;
import com.team5_jsb.domain.user.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MyPageController {
    private final UserService userService;
    private final QuestionRepository questionRepository;
    private final AnswerService answerService;

    @GetMapping("/mypage")
    public String myPage(Authentication authentication,
                         @RequestParam(defaultValue = "0") int page,
                         @RequestParam(name = "answerPage", defaultValue = "0") int answerPage,
                         Model model) {
        User user = getCurrentUser(authentication);
        Long userId = user.getId();

        // 유저 정보
        model.addAttribute("user", userService.getUserProfile(userId));

        // 내가 쓴 질문(최신순, 페이지 10개)
        Pageable pageable = PageRequest.of(page, 5);
        Page<Question> questions = questionRepository.findByAuthor_IdOrderByCreatedDateDesc(userId, pageable);
        model.addAttribute("questions", questions);

        // 내가 쓴 답변
        Page<Answer> myAnswers = answerService.getMyAnswers(userId, answerPage);
        model.addAttribute("answers", myAnswers);

        return "mypage";
    }
    
    
     // 일반 로그인(CustomUserDetails), Google 소셜 로그인(CustomOidcUser) 모두 대응
    private User getCurrentUser(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        
        if (principal instanceof CustomUserDetails) {
            // 일반 로그인 사용자
            return ((CustomUserDetails) principal).getUser();
        } else if (principal instanceof CustomOidcUser) {
            // Google 소셜 로그인 사용자
            return ((CustomOidcUser) principal).getUser();
        }
        
        throw new IllegalArgumentException("지원하지 않는 Principal 타입입니다: " + principal.getClass().getSimpleName());
    }
}