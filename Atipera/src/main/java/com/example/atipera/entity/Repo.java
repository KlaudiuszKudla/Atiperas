package com.example.atipera.entity;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class Repo {
    private String OwnerLogin;
    private String RepositoryName;
    private List<Branch> branch;
}
