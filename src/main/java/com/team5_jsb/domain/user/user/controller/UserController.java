package com.team5_jsb.domain.user.user.controller;

import com.team5_jsb.domain.user.user.dto.SignupRequest;
import com.team5_jsb.domain.user.user.entity.User;
import com.team5_jsb.domain.user.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "user/login";
    }

    @GetMapping("/signup")
    public String signupPage(Model model) {
        model.addAttribute("signupRequest", new SignupRequest());
        return "user/signup";
    }

    @PostMapping("/signup")
    public String signup(
            @Valid @ModelAttribute
            SignupRequest request,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

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
