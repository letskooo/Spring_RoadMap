package com.example.jwt.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class MyFilter3 implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        if (req.getMethod().equals("POST")){
            log.info("POST 요청됨");
            String headerAuth = req.getHeader("Authorization"); // 리턴 타입은 String

            log.info(headerAuth);
            log.info("===========filter3=============");

            if (headerAuth.equals("cos")){
                chain.doFilter(req, resp);
            } else {
                PrintWriter outPrintWriter = resp.getWriter();
                outPrintWriter.println("no pass");
            }
        }
    }
}
