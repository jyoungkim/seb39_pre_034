package com.codestates.stackoverflowclone.answer.service;

import com.codestates.stackoverflowclone.answer.dto.AnswerPatchDto;
import com.codestates.stackoverflowclone.answer.dto.AnswerPostDto;
import com.codestates.stackoverflowclone.answer.entity.Answer;
import com.codestates.stackoverflowclone.answer.repository.AnswerRepository;
import com.codestates.stackoverflowclone.exception.BusinessLogicException;
import com.codestates.stackoverflowclone.exception.ExceptionCode;
import com.codestates.stackoverflowclone.member.entity.Member;
import com.codestates.stackoverflowclone.question.entity.Question;
import com.codestates.stackoverflowclone.question.service.QuestionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {

    private final QuestionService questionService;
    private final AnswerRepository answerRepository;

    public AnswerService(QuestionService questionService, AnswerRepository answerRepository) {
        this.questionService = questionService;
        this.answerRepository = answerRepository;
    }

    public Question creteAnswer(long answerId, AnswerPostDto answerPostDto, Member member) {
        Answer answer = new Answer();
        Question verifiedQuestion = questionService.findVerifiedQuestion(answerId);

        answer.setContent(answerPostDto.getContent());
        answer.setQuestion(verifiedQuestion);
        answer.setMember(member);

        answerRepository.save(answer);

        return verifiedQuestion;
    }

    public void updateAnswer(AnswerPatchDto answerPatchDto) {
        Answer findAnswer = findVerifiedAnswer(answerPatchDto.getAnswerId());

        Optional.ofNullable(answerPatchDto.getContent())
                .ifPresent(content -> findAnswer.setContent(content));

        answerRepository.save(findAnswer);
    }


    public void deleteAnswer(long answerId) {
        Answer verifiedAnswer = findVerifiedAnswer(answerId);
        answerRepository.delete(verifiedAnswer);
    }

    public void acceptAnswer(long answerId) {
        Answer verifiedAnswer = findVerifiedAnswer(answerId);
        Question verifiedQuestion = questionService.findVerifiedQuestion(verifiedAnswer.getQuestion().getQuestionId());
        List<Answer> answerList = verifiedQuestion.getAnswerList();
        for (int i = 0; i < answerList.size(); i++) {
            if (answerList.get(i).getIsAccepted() == 1) {
                System.out.println("이미 확정된 답변이 있습니다.");
            }
        }
        verifiedAnswer.setIsAccepted(1);
        answerRepository.save(verifiedAnswer);
    }

    @Transactional(readOnly = true)
    public Answer findVerifiedAnswer(long answerId) {
        Optional<Answer> optionalAnswer = answerRepository.findById(answerId);
        Answer findAnswer =
                optionalAnswer.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.ANSWER_NOT_FOUND));
        return findAnswer;
    }
}
