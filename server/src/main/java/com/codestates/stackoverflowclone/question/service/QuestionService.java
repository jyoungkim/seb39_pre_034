package com.codestates.stackoverflowclone.question.service;

import com.codestates.stackoverflowclone.exception.BusinessLogicException;
import com.codestates.stackoverflowclone.exception.ExceptionCode;
import com.codestates.stackoverflowclone.member.entity.Member;
import com.codestates.stackoverflowclone.question.dto.QuestionPatchDto;
import com.codestates.stackoverflowclone.question.dto.QuestionPostDto;
import com.codestates.stackoverflowclone.question.dto.QuestionTagDto;
import com.codestates.stackoverflowclone.question.entity.Question;
import com.codestates.stackoverflowclone.question.entity.QuestionDownvoteMember;
import com.codestates.stackoverflowclone.question.entity.QuestionUpvoteMember;
import com.codestates.stackoverflowclone.question.repository.QuestionDownvoteMemberRepository;
import com.codestates.stackoverflowclone.question.repository.QuestionRepository;
import com.codestates.stackoverflowclone.question.repository.QuestionUpvoteMemberRepository;
import com.codestates.stackoverflowclone.tag.entity.Tag;
import com.codestates.stackoverflowclone.tag.repository.TagRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class QuestionService {

    private QuestionRepository questionRepository;
    private QuestionUpvoteMemberRepository questionUpvoteMemberRepository;
    private QuestionDownvoteMemberRepository questionDownvoteMemberRepository;
    private TagRepository tagRepository;

    public QuestionService(QuestionRepository questionRepository, QuestionUpvoteMemberRepository questionUpvoteMemberRepository, QuestionDownvoteMemberRepository questionDownvoteMemberRepository, TagRepository tagRepository) {
        this.questionRepository = questionRepository;
        this.questionUpvoteMemberRepository = questionUpvoteMemberRepository;
        this.questionDownvoteMemberRepository = questionDownvoteMemberRepository;
        this.tagRepository = tagRepository;
    }

    public Page<Question> findQuestions(int page, int size) {
        return questionRepository.findAll(PageRequest.of(page, size, Sort.by("QuestionId").descending()));
    }

    public List<Question> searchQuestions(int page, int size, String keyword) {
        return questionRepository.findByKeyword(keyword);
    }

    public Question findQuestion(long questionId) {
        Question verifiedQuestion = findVerifiedQuestion(questionId);
        verifiedQuestion.setViews(verifiedQuestion.getViews()+1);
        return verifiedQuestion;
    }

    public Question saveQuestion(QuestionPostDto questionPostDto, Member member) {
        Question question = new Question();
        question.setTitle(questionPostDto.getTitle());
        question.setContent(questionPostDto.getContent());
        question.setMember(member);

        if (questionPostDto.getTagList() != null) {
            List<QuestionTagDto> tagList = questionPostDto.getTagList();
            for (QuestionTagDto tagDto : tagList
            ) {
                Tag tag = new Tag();
                tag.setTagName(tagDto.getTagName());
                tag.setQuestion(question);
                tagRepository.save(tag);
            }
        }
        return questionRepository.save(question);
    }

    public Question saveQuestion2(QuestionPostDto questionPostDto, Member member) {
        Question question = new Question();
        question.setTitle(questionPostDto.getTitle());
        question.setContent(questionPostDto.getContent());
        question.setMember(member);

        Question savedQuestion = questionRepository.save(question);
        return savedQuestion;
    }

    public Question updateQuestion(QuestionPatchDto questionPatchDto) {
        Question findQuestion = findVerifiedQuestion(questionPatchDto.getQuestionId());

        Optional.ofNullable(questionPatchDto.getTitle())
                .ifPresent(title -> findQuestion.setTitle(title));
        Optional.ofNullable(questionPatchDto.getContent())
                .ifPresent(content -> findQuestion.setContent(content));

        return questionRepository.save(findQuestion);
    }

    public void deleteQuestion(long questionId) {
        Question verifiedQuestion = findVerifiedQuestion(questionId);
        questionRepository.delete(verifiedQuestion);
    }

    public void upVote(Question question, Member member) {
        QuestionUpvoteMember questionUpvoteMember = new QuestionUpvoteMember();
        questionUpvoteMember.setQuestion(question);
        questionUpvoteMember.setMember(member);
        questionUpvoteMemberRepository.save(questionUpvoteMember);

        question.getUpvoteMembers().add(questionUpvoteMember);

        questionRepository.save(question);
    }

    public void downVote(Question question, Member member) {

        QuestionDownvoteMember questionDownvoteMember = new QuestionDownvoteMember();
        questionDownvoteMember.setQuestion(question);
        questionDownvoteMember.setMember(member);
        questionDownvoteMemberRepository.save(questionDownvoteMember);

        question.getDownvoteMembers().add(questionDownvoteMember);

        questionRepository.save(question);
    }

    @Transactional(readOnly = true)
    public Question findVerifiedQuestion(long questionId) {
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);
        Question findQuestion =
                optionalQuestion.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));
        return findQuestion;
    }


}
