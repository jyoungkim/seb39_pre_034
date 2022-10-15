package com.codestates.stackoverflowclone.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason="Login_Required")
public class LoginRequiredException extends RuntimeException{
}
