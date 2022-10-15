package com.codestates.stackoverflowclone.answer.dto;

import lombok.Getter;

@Getter
public class AnswerPatchDto {

    private long answerId;

    private String content;

    public void setAnswerId(long answerId) {
        this.answerId = answerId;
    }
}
