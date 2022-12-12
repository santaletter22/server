package com.project.santaletter.security.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class authenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {


        //TODO: 로그인 성공 시 원래 있던 페이지로 돌아가기
        setDefaultTargetUrl("/");

        // 에러 세션 지우기
        clearAuthenticationAttributes(request);

        // redirect
        redirectStrategy.sendRedirect(request, response, getDefaultTargetUrl());
    }

}