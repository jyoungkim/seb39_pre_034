package com.codestates.stackoverflowclone.answer.controller;

import com.codestates.stackoverflowclone.answer.dto.AnswerPatchDto;
import com.codestates.stackoverflowclone.answer.dto.AnswerPostDto;
import com.codestates.stackoverflowclone.answer.entity.Answer;
import com.codestates.stackoverflowclone.answer.service.AnswerService;
import com.codestates.stackoverflowclone.dto.SingleResponseDto;
import com.codestates.stackoverflowclone.member.entity.Member;
import com.codestates.stackoverflowclone.member.service.MemberService;
import com.codestates.stackoverflowclone.question.entity.Question;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.security.Principal;


@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = "http://s3-clone-sof.s3-website.ap-northeast-2.amazonaws.com", allowCredentials = "true")
public class AnswerController {

    private final AnswerService answerService;
    private final MemberService memberService;

    public AnswerController(AnswerService answerService, MemberService memberService) {
        this.answerService = answerService;
        this.memberService = memberService;
    }

    @GetMapping("/answers/{answerId}")
    public ResponseEntity getQuestion(@PathVariable("answerId") @Positive long answerId) {

        Answer findAnswer = answerService.findVerifiedAnswer(answerId);

        return new ResponseEntity<>(
                new SingleResponseDto<>(findAnswer), HttpStatus.OK);
    }
    @Secured("ROLE_USER")
    @PostMapping("/questions/{questionId}")
    public ResponseEntity postAnswer(@PathVariable("questionId") long postId, @RequestBody AnswerPostDto answerPostDto, Principal principal) {

        Member member = null;
        if (principal != null) {
            member = memberService.getMember(principal.getName());
        }
        answerService.creteAnswer(postId, answerPostDto, member);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/answers/{answerId}")
    public ResponseEntity patchMember(@PathVariable("answerId") long answerId, @RequestBody AnswerPatchDto requestBody) {

        requestBody.setAnswerId(answerId);
        answerService.updateAnswer(requestBody);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/answers/{answerId}")
    public ResponseEntity deleteAnswer(@PathVariable("answerId") long answerId) {
        answerService.deleteAnswer(answerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/answers/accept/{answerId}")
    public ResponseEntity acceptAnswer(@PathVariable("answerId") long answerId) {
        answerService.acceptAnswer(answerId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
