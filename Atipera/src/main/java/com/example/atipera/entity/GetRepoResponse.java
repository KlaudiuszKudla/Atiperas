package com.example.atipera.entity;

import lombok.Getter;


@Getter
public class GetRepoResponse {
    private final String code;
    private final Code message;

    public GetRepoResponse(Code code) {
        this.code = code.label;
        this.message = code;
    }
}

