package com.example.atipera.Controller;

import com.example.atipera.entity.Code;
import com.example.atipera.entity.GetRepoResponse;
import com.example.atipera.entity.Repo;
import com.example.atipera.entity.UsernameDto;
import com.example.atipera.exceptions.UnacceptableFormat;
import com.example.atipera.exceptions.UserNotExistWithName;
import com.example.atipera.services.RepoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RepoController {

    private final RepoService repoService;

    @RequestMapping(path = "/repos", method = RequestMethod.POST, produces = {MediaType.APPLICATION_ATOM_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getRepo(@RequestBody UsernameDto user, HttpServletRequest request){
        try {
            List<Repo> repositories = repoService.getRepoByUsername(user.getUsername(), request);
            return ResponseEntity.ok(repositories);
        }
        catch (UnacceptableFormat e ){
            log.info("Unacceptable format");
            return ResponseEntity.status(404).body(new GetRepoResponse(Code.UNACCEPTABLE_FORMAT));
        }
        catch (UserNotExistWithName e ){
            log.info("User doesent exist");
            return ResponseEntity.status(404).body(new GetRepoResponse(Code.USER_NOT_EXIST_WITH_NAME));
        }

    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ResponseEntity<GetRepoResponse> handleMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex) {
        log.info("Unsupported media type");
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new GetRepoResponse(Code.UNACCEPTABLE_FORMAT));
    }
}
