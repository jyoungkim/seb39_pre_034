package com.codestates.stackoverflowclone.member.repository;

import com.codestates.stackoverflowclone.answer.entity.Answer;
import com.codestates.stackoverflowclone.member.entity.Member;
import com.codestates.stackoverflowclone.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByUsername(String username);
}
