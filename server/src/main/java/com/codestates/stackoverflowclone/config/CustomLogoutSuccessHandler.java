package com.codestates.stackoverflowclone.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.addHeader("Access-Control-Allow-Origin","http://s3-clone-sof.s3-website.ap-northeast-2.amazonaws.com");
        response.addHeader("Access-Control-Allow-Credentials","true");
        response.setStatus(200);
    }
}
