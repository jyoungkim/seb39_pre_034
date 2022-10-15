package com.codestates.stackoverflowclone.answer.entity;

import com.codestates.stackoverflowclone.member.entity.Member;
import com.codestates.stackoverflowclone.question.entity.Question;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "questionId")
    private Question question;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    @Column(columnDefinition = "TINYINT", length = 1)
    @ColumnDefault("0")
    private int isAccepted;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime modifiedAt = LocalDateTime.now();
}