package com.codestates.stackoverflowclone.testcontroller;

import lombok.Data;

import java.util.List;


@Data
public class TestPostDto {
    private String title;

    private List<TagPostDto> tagList;
}
