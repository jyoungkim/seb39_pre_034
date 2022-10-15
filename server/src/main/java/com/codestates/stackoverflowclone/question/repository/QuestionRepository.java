package com.codestates.stackoverflowclone.question.repository;

import com.codestates.stackoverflowclone.question.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query(value = "SELECT * FROM QUESTION WHERE title LIKE %?1% OR content LIKE %?1%", nativeQuery = true)
    List<Question> findByKeyword(String keyword);
}
