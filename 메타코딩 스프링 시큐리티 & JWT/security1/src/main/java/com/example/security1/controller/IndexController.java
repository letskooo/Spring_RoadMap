package com.example.security1.controller;

import com.example.security1.Model.User;
import com.example.security1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    // localhost:8080/
    // localhost:8080
    @GetMapping({"", "/"})
    public String index(){
        return "index";
    }

    @GetMapping("/user")
    @ResponseBody
    public String user(){
        return "user";
    }

    @GetMapping("/admin")
    @ResponseBody
    public String admin(){
        return "admin";
    }
    @GetMapping("/manager")
    @ResponseBody
    public String manager(){
        return "manager";
    }

    @PostMapping("/join")
    public String join(User user){
        System.out.println(user);

        user.setRole("ROLE_USER");

        String rawPassword = user.getPassword();
        String encodePassword = passwordEncoder.encode(rawPassword);

        user.setPassword(encodePassword);

        userRepository.save(user);

        return "redirect:/loginForm";
    }


    @GetMapping("/loginForm")
    public String loginForm(){
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm(){
        return "joinForm";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/info")
    @ResponseBody
    public String info(){
        return "개인 정보";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')") // 접근 권한 다중 설정
    // @PreAuthorize("hasRole('ADMIN')")            // 접근 권한 단일 설정
    @GetMapping("/data")
    @ResponseBody
    public String data(){
        return "개인 정보";
    }

}