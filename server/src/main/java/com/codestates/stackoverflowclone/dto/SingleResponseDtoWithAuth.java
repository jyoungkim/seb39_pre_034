package com.codestates.stackoverflowclone.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SingleResponseDtoWithAuth<T> {
    private T data;
    private String authenticatedName;
}