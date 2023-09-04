package com.example.atipera.WebClient;

import com.example.atipera.WebClient.dto.RepoMainDto;
import com.example.atipera.WebClient.dto.BranchDto;
import com.example.atipera.entity.Branch;
import com.example.atipera.entity.Repo;
import com.example.atipera.exceptions.UnacceptableFormat;
import com.example.atipera.exceptions.UserNotExistWithName;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Component
@Slf4j
public class RepoClient {

    @Value("${api.url}")
    private String API_URL;
    private RestTemplate restTemplate = new RestTemplate();

    public List<Repo> getReposForUsername(String username, HttpServletRequest request) throws UserNotExistWithName, UnacceptableFormat {
        List<RepoMainDto> repoMainDtoList = new ArrayList<>();
        try{
            repoMainDtoList = getAllInfo(username, request);
        } catch(HttpClientErrorException.NotFound e) {
            throw new UserNotExistWithName("User doesent exist with name");
        }
        List<Repo> repoList = processRepoMainDtoList(repoMainDtoList);
        return repoList;
    }

    public List<RepoMainDto> getAllInfo(String username, HttpServletRequest request) {
        String fullUrl = API_URL + "/" + username + "/repos";
        ResponseEntity<List<RepoMainDto>> response = restTemplate.exchange(
                fullUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<RepoMainDto>>() {
                },
                username
        );
        MediaType contentType = response.getHeaders().getContentType();
        if (!contentType.toString().contains(request.getHeader("Accept").toString())) {
            throw new UnacceptableFormat("Unacceptable format");
        }
        List<RepoMainDto> repoMainDtoList = response.getBody();
        return repoMainDtoList;
    }

    private List<Repo> processRepoMainDtoList(List<RepoMainDto> repoMainDtoList) {
        List<Repo> repoList = new ArrayList<>();
        for (RepoMainDto repoMainDto : repoMainDtoList) {
            if (!repoMainDto.isFork()) {
                List<BranchDto> branchDtoList = getAllBranches(repoMainDto);
                if (branchDtoList != null) {
                    List<Branch> branchInfoList = mapToBranchList(branchDtoList);
                    Repo repo = createRepoDto(repoMainDto, branchInfoList);
                    repoList.add(repo);
                }
            }
        }
        return repoList;
    }

    private List<BranchDto> getAllBranches(RepoMainDto repoMainDto) {
        String branchesUrl = repoMainDto.getBranches_url().replace("{/branch}", "");
        ResponseEntity<List<BranchDto>> branchesResponse = restTemplate.exchange(
                branchesUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<BranchDto>>() {
                }
        );
        return branchesResponse.getBody();
    }

    private List<Branch> mapToBranchList(List<BranchDto> branchDtoList) {
        List<Branch> branchInfoList = new ArrayList<>();
        for (BranchDto branchDto : branchDtoList) {
            branchInfoList.add(new Branch(branchDto.getName(), branchDto.getCommit().getSha()));
        }
        return branchInfoList;
    }

    private Repo createRepoDto(RepoMainDto repoMainDto, List<Branch> branchInfoList) {
        return Repo.builder()
                .OwnerLogin(repoMainDto.getOwner().getLogin())
                .RepositoryName(repoMainDto.getName())
                .branch(branchInfoList)
                .build();
    }
}
