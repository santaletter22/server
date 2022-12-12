package com.project.santaletter.security.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class SecuritySessionExpiredStrategy implements SessionInformationExpiredStrategy {

    private static final String defaultUrl = "/login";

    @Override
    public void onExpiredSessionDetected(
            SessionInformationExpiredEvent event) throws IOException {

        HttpServletRequest request = event.getRequest();
        HttpServletResponse response = event.getResponse();

        String ajaxHeader = request.getHeader("X-Requested-With");
        boolean isAjax = "XMLHttpRequest".equals(ajaxHeader);

        if (isAjax) { // session 이 만료된 상태에서 Ajax 요청 시 Response 보내기
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("세션이 만료되었습니다. 다시 로그인 해주세요.");
        } else { // session 이 만료된 상태에서 Web 요청 시 Login Page 로 이동
            response.sendRedirect(defaultUrl);
        }
    }
}
