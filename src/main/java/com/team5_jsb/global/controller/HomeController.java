package com.team5_jsb.global.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
    
    @GetMapping("/")
    @ResponseBody
    public String home() {
        return "<h1>Welcome to Team5 JSB!</h1><p>로그인 성공!</p><a href='/user/logout'>로그아웃</a>";
    }
}