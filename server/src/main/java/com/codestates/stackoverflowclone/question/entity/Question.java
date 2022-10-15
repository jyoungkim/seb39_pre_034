package com.codestates.stackoverflowclone.question.entity;

import com.codestates.stackoverflowclone.answer.entity.Answer;
import com.codestates.stackoverflowclone.member.entity.Member;
import com.codestates.stackoverflowclone.tag.entity.Tag;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @JsonManagedReference
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;

    @ColumnDefault("0")
    private long views;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    @JsonManagedReference
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<QuestionUpvoteMember> upvoteMembers;

    @JsonManagedReference
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<QuestionDownvoteMember> downvoteMembers;

    @JsonManagedReference
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Tag> tagList;


    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime modifiedAt = LocalDateTime.now();
}
