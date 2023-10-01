package com.example.security1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class IndexController {

    // localhost:8080/
    // localhost:8080
    @GetMapping({"", "/"})
    public String index(){
        return "index";
    }

    @GetMapping("/user")
    public String user(){
        return "user";
    }

    @GetMapping("/admin")
    public String admin(){
        return "admin";
    }
    @GetMapping("/manager")
    public String manager(){
        return "manager";
    }
    @GetMapping("/join")
    public String join(){
        return "join";
    }
    @GetMapping("/login")
    @ResponseBody
    public String login(){
        return "login";
    }
    @GetMapping("/joinProc")
    @ResponseBody
    public String joinProc(){
        return "회원가입 완료됨";
    }
}