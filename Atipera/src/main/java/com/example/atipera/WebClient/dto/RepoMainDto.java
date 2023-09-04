package com.example.atipera.WebClient.dto;

import lombok.Getter;

@Getter
public class RepoMainDto {

    private RepoOwner owner;
    private String name;
    private String branches_url;
    private boolean fork;
}
