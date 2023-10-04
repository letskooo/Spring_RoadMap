package com.example.security1.controller;

import com.example.security1.Model.User;
import com.example.security1.config.auth.PrincipalDetails;
import com.example.security1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
@Slf4j
public class IndexController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/test/login")
    @ResponseBody
    public String testLogin(Authentication authentication, @AuthenticationPrincipal PrincipalDetails userDetails){ // security core
        log.info("/test/login =================");
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        log.info("authentication= {}", principalDetails.getUser());
        log.info("userDetails= {}", userDetails.getUser());

        return "세션 정보 확인하기";
    }

    @GetMapping("/test/oauth/login")
    @ResponseBody
    public String testOauthLogin(Authentication authentication,
                                 @AuthenticationPrincipal OAuth2User oAuth){ // security core
        log.info("=============/test/oauth/login =================");
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        log.info("authentication= {}", oAuth2User.getAttributes());
        log.info("oauth2User= {}", oAuth.getAttributes());

        return "OAuth 세션 정보 확인하기";
    }




    // localhost:8080/
    // localhost:8080
    @GetMapping({"", "/"})
    public String index(){
        return "index";
    }

    // 일반 로그인을 하던, OAuth2 로그인을 하던 PrincipalDetails 타입으로 받을 수 있음
    @GetMapping("/user")
    @ResponseBody
    public String user(@AuthenticationPrincipal PrincipalDetails principalDetails){

        log.info("principalDetails={}", principalDetails.getUser());

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