package com.codestates.stackoverflowclone.answer.repository;

import com.codestates.stackoverflowclone.answer.entity.Answer;
import com.codestates.stackoverflowclone.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
