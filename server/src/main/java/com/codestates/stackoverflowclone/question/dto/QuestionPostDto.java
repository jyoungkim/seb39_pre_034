package com.codestates.stackoverflowclone.question.dto;


import com.codestates.stackoverflowclone.testcontroller.TagPostDto;
import lombok.Getter;

import java.util.List;

@Getter
public class QuestionPostDto {

    private String title;

    private String content;

    private List<QuestionTagDto> tagList;
}
