package com.codestates.stackoverflowclone.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        //response.setStatus(401);
        response.addHeader("Access-Control-Allow-Origin","http://s3-clone-sof.s3-website.ap-northeast-2.amazonaws.com");
        response.addHeader("Access-Control-Allow-Credentials","true");
        response.sendError(401, "wrong_login_info");
    }
}
