package com.team5_jsb.domain.user.user.controller;

import com.team5_jsb.domain.user.user.dto.LoginRequest;
import com.team5_jsb.domain.user.user.dto.SignupRequest;
import com.team5_jsb.domain.user.user.entity.User;
import com.team5_jsb.domain.user.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("loginRequest", new LoginRequest());
        return "user/login";
    }

    @GetMapping("/signup")
    public String signupPage(Model model) {
        model.addAttribute("signupRequest", new SignupRequest());
        return "user/signup";
    }

    @PostMapping("/login-validate")
    public String loginValidate(@Valid @ModelAttribute LoginRequest loginRequest, 
                               BindingResult bindingResult, 
                               Model model, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("loginRequest", loginRequest);
            return "user/login";
        }
        // 검증 통과 후 Spring Security 자동 인증 처리
        try {
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    );

            Authentication auth = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(auth);

            return "redirect:/";  // 로그인 성공 시 홈으로

        } catch (AuthenticationException e) {
            bindingResult.rejectValue("username", "login.failed",
                    "아이디 또는 비밀번호가 잘못되었습니다.");
            return "user/login";
        }
    }

    @PostMapping("/signup")
    public String signup(
            @Valid @ModelAttribute SignupRequest request,
            @RequestParam String passwordConfirm,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        // 비밀번호 일치 검증
        if (!request.getPassword().equals(passwordConfirm)) {
            model.addAttribute("passwordMismatch", true);
            model.addAttribute("signupRequest", request);
            return "user/signup";
        }

        if(bindingResult.hasErrors()){
            return "user/signup";
        }

        try{
            User user = userService.register(request.getUsername(), request.getPassword(), request.getEmail());
            redirectAttributes.addFlashAttribute("message", "회원가입이 완료되었습니다.");
            return "redirect:/user/login";
        } catch (IllegalArgumentException e){
            bindingResult.rejectValue("username", "duplicate", e.getMessage());
            return "user/signup";
        } catch (RuntimeException e){
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "user/signup";
        }
    }


}
