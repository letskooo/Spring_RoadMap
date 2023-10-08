package com.example.jwt.config.jwt;

import com.example.jwt.config.auth.PrincipalDetails;
import com.example.jwt.model.APIUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.parameters.P;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.BufferedReader;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    // /login POST 요청이 오면 로그인 시도를 위해서 실행되는 함수

    //  == attemptAuthentication() 에서 해줘야 할 것 ==
    // 1. username, password를 받아서
    // 2. 정상인지 로그인 시도. authenticationManager로 로그인 시도를 하면
    //      PrincipalDetailsService가 호출되면서 loadUserByUsername() 메소드 실행
    // 3. PrincipalDetails를 세션에 담고 (권한 관리를 위해서)
    // 4. JWT 토큰을 만들어서 응답해주면 됨

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        log.info("trying login....");

        try {

            ObjectMapper om = new ObjectMapper();
            APIUser apiUser = om.readValue(request.getInputStream(), APIUser.class);
            System.out.println(apiUser);

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(apiUser.getUsername(), apiUser.getPassword());

            Authentication authentication =
                    authenticationManager.authenticate(authenticationToken);

            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            System.out.println(principalDetails.getUsername());

            return authentication;

        } catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }
}