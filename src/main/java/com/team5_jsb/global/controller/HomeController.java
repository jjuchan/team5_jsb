package com.team5_jsb.global.controller;

import com.team5_jsb.domain.user.user.dto.CustomOidcUser;
import com.team5_jsb.domain.user.user.dto.CustomUserDetails;
import com.team5_jsb.domain.user.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
    
    @GetMapping("/")
    public String home(Authentication authentication, Model model) {
        if(authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
            User user = getCurrentUser(authentication);
            model.addAttribute("user", user);
            model.addAttribute("username", user.getUsername());
            model.addAttribute("email", user.getEmail());
        }
        return "index";
    }

    @GetMapping("/home")
    public String homeRedirect() {
        return "redirect:/";
    }
    
    // 일반 로그인(CustomUserDetails), Google 소셜 로그인(CustomOidcUser) 모두 대응
    private User getCurrentUser(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        
        if (principal instanceof CustomUserDetails) {
            return ((CustomUserDetails) principal).getUser();
        } else if (principal instanceof CustomOidcUser) {
            return ((CustomOidcUser) principal).getUser();
        }
        
        throw new IllegalArgumentException("지원하지 않는 Principal 타입입니다: " + principal.getClass().getSimpleName());
    }
}