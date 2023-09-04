package com.example.atipera.WebClient.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BranchDto {

    private String name;
    private Commit commit;
}
