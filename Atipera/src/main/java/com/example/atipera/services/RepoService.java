package com.example.atipera.services;

import com.example.atipera.WebClient.RepoClient;
import com.example.atipera.entity.Repo;
import com.example.atipera.exceptions.UnacceptableFormat;
import com.example.atipera.exceptions.UserNotExistWithName;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RepoService {

    private final RepoClient repoClient;

    public List<Repo> getRepoByUsername(String username, HttpServletRequest request) throws UserNotExistWithName, UnacceptableFormat
{
        try {
            List<Repo> response = repoClient.getReposForUsername(username, request);
            return response;
        }
        catch (UserNotExistWithName e){
            throw new UserNotExistWithName("User doesent exist with this name");
        }
        catch (UnacceptableFormat e ){
            throw new UnacceptableFormat("Format not acceptable");
        }
    }


}
