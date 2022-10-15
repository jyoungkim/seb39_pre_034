package com.codestates.stackoverflowclone.tag.entity;

import com.codestates.stackoverflowclone.question.entity.Question;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagId;

    private String tagName;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "questionId")
    private Question question;
}
