package com.codestates.stackoverflowclone.question.repository;

import com.codestates.stackoverflowclone.question.entity.QuestionUpvoteMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionUpvoteMemberRepository extends JpaRepository<QuestionUpvoteMember, Long> {
}
