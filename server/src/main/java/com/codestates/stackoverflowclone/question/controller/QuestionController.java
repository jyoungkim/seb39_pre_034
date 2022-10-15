package com.codestates.stackoverflowclone.question.controller;

import com.codestates.stackoverflowclone.config.auth.PrincipleDetails;
import com.codestates.stackoverflowclone.dto.MultiResponseDto;
import com.codestates.stackoverflowclone.dto.SingleResponseDto;
import com.codestates.stackoverflowclone.member.entity.Member;
import com.codestates.stackoverflowclone.member.service.MemberService;
import com.codestates.stackoverflowclone.question.dto.QuestionPatchDto;
import com.codestates.stackoverflowclone.question.dto.QuestionPostDto;
import com.codestates.stackoverflowclone.question.entity.Question;
import com.codestates.stackoverflowclone.question.service.QuestionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = "http://s3-clone-sof.s3-website.ap-northeast-2.amazonaws.com", allowCredentials = "true")
public class QuestionController {

    private final QuestionService questionService;
    private final MemberService memberService;

    public QuestionController(QuestionService questionService, MemberService memberService) {
        this.questionService = questionService;
        this.memberService = memberService;
    }

    @GetMapping("/questions")
    public ResponseEntity getQuestions(@Positive @RequestParam int page,
                                       @Positive @RequestParam int size) {
        Page<Question> pageQuestions = questionService.findQuestions(page - 1, size);
        List<Question> questions = pageQuestions.getContent();


        return new ResponseEntity<>(
                new MultiResponseDto<>(questions, pageQuestions), HttpStatus.OK);
    }

    @GetMapping("/questions/search")
    public ResponseEntity searchQuestions(@Positive @RequestParam int page,
                                          @Positive @RequestParam int size,
                                          @RequestParam(value = "keyword") String keyword) {
        List<Question> listQuestions = questionService.searchQuestions(page - 1, size, keyword);
        Page<Question> pageQuestions = new PageImpl<>(listQuestions);

        return new ResponseEntity<>(
                new MultiResponseDto<>(listQuestions, pageQuestions), HttpStatus.OK);
    }

    @GetMapping("/questions/{questionId}")
    public ResponseEntity getQuestion(@PathVariable("questionId") @Positive long questionId, @AuthenticationPrincipal PrincipleDetails principleDetails, Model model) {

        Question findQuestion = questionService.findQuestion(questionId);
        String username = "";

        if (principleDetails == null) {
            System.out.println("인증(로그인)된 사용자 정보 없음");
        }

        if (principleDetails != null) {
            System.out.println("인증(로그인)된 사용자 정보 담겨 있음");
            username = principleDetails.getUsername();
        }
        return new ResponseEntity<>(
                new SingleResponseDto<>(findQuestion), HttpStatus.OK);
    }

    /* 원본
    @PostMapping("questions/ask")
    public ResponseEntity postQuestion(@RequestBody QuestionPostDto postBody, Principal principal) {

        Member member = null;
        if (principal != null) {
            member = memberService.getMember(principal.getName());
        }
        Question createdQuestion = questionService.saveQuestion(postBody, member);

        return new ResponseEntity<>(
                new SingleResponseDto<>(createdQuestion), HttpStatus.CREATED);
    }*/

    @PostMapping("questions/ask")
    public ResponseEntity postQuestion(@RequestBody QuestionPostDto postBody, Principal principal) {

        Member member = null;
        if (principal != null) {
            member = memberService.getMember(principal.getName());
        }
        Question createdQuestion = questionService.saveQuestion(postBody, member);


        return new ResponseEntity<>(
                new SingleResponseDto<>(createdQuestion), HttpStatus.CREATED);
    }

    @PostMapping("questions/ask/test")
    public ResponseEntity postTestQuestion(@RequestBody QuestionPostDto postBody, Principal principal) {
        Member member = memberService.getMember(principal.getName());

        Question createdQuestion = questionService.saveQuestion2(postBody, member);
        createdQuestion.setMember(member);

        return new ResponseEntity<>(
                new SingleResponseDto<>(createdQuestion), HttpStatus.CREATED);
    }

    @PatchMapping("/questions/{questionId}")
    public ResponseEntity patchMember(@PathVariable("questionId") @Positive long questionId, @RequestBody QuestionPatchDto requestBody) {
        requestBody.setQuestionId(questionId);

        Question question = questionService.updateQuestion(requestBody);

        return new ResponseEntity<>(
                new SingleResponseDto<>(question),
                HttpStatus.OK);
    }

    @DeleteMapping("/questions/{questionId}")
    public ResponseEntity deleteQuestion(@PathVariable("questionId") @Positive long questionId) {
        questionService.deleteQuestion(questionId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/questions/upvote/{questionId}")
    public ResponseEntity upvoteQuestion(@PathVariable() @Positive long questionId, Principal principal) {

        Question verifiedQuestion = questionService.findVerifiedQuestion(questionId);
        Member member = memberService.getMember(principal.getName());

        questionService.upVote(verifiedQuestion, member);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/questions/downvote/{questionId}")
    public ResponseEntity downvoteQuestion(@PathVariable() @Positive long questionId, Principal principal) {

        Question verifiedQuestion = questionService.findVerifiedQuestion(questionId);
        Member member = memberService.getMember(principal.getName());

        questionService.downVote(verifiedQuestion, member);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
