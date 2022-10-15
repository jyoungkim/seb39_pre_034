package com.codestates.stackoverflowclone.question.entity;


import com.codestates.stackoverflowclone.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class QuestionUpvoteMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionUpvoteId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "questionId")
    private Question question;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;
}
