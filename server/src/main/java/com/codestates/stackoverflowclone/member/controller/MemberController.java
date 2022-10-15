package com.codestates.stackoverflowclone.member.controller;

import com.codestates.stackoverflowclone.exception.LoginRequiredException;
import com.codestates.stackoverflowclone.member.dto.MemberSignupDto;
import com.codestates.stackoverflowclone.member.entity.Member;
import com.codestates.stackoverflowclone.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = "http://s3-clone-sof.s3-website.ap-northeast-2.amazonaws.com", allowCredentials = "true")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }


    @GetMapping("/users/login")
    public ResponseEntity requireLogin() {
        Map<String, String> errMsg = new HashMap<>();
        errMsg.put("message", "login_required");

        return new ResponseEntity(errMsg, HttpStatus.UNAUTHORIZED);
    }

    /**
    * 원본
    @CrossOrigin(origins="*")
    @GetMapping("/users/login")
    public ResponseEntity requireLogin() {
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }*/

    @GetMapping("/users/login/success")
    public ResponseEntity loginSuccess() {
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/users/logout/success")
    public ResponseEntity logoutSuccess() {
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/users/signup")
    public ResponseEntity createMember(@RequestBody MemberSignupDto memberSignupDto) {
        Member createdMember = memberService.signupMember(memberSignupDto);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/users/info")
    public ResponseEntity getUserInfo(Principal principal) {
        Map<String, String> result = new HashMap<>();

        if (principal == null) {
            result.put("isAuthenticated", "n");
            result.put("username", "");
        } else {
            result.put("isAuthenticated", "y");
            result.put("username", principal.getName());
        }

        System.out.println("인증 : "+result.get("isAuthenticated"));
        System.out.println("이름 : "+result.get("username"));
        return new ResponseEntity(result, HttpStatus.OK);
    }

    public void getUserInfo2(@AuthenticationPrincipal Authentication authentication) {
        boolean authenticated = authentication.isAuthenticated();
        System.out.println(authenticated);

    }
}
