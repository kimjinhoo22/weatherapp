package com.acon.weatherapp.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.net.URLEncoder;

public class CustomAuthfailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        System.out.println("여기 요청은 도착햇음");

        String errorMessage = "null";
        if(exception instanceof BadCredentialsException){
            errorMessage = "아이디와 비밀번호를 확인해주세요";
        }else if(exception instanceof UsernameNotFoundException){
            errorMessage = "존재 하지 않는 계정입니다.";
        }

        String redirectUrl = String.format("/?errorMessage=%s", URLEncoder.encode(errorMessage, "UTF-8"));
        response.sendRedirect(redirectUrl);
    }
}
