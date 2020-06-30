package com.Eragoo.Blog.auth;

import com.Eragoo.Blog.auth.dto.Token;
import com.Eragoo.Blog.security.UserAuthenticationCommand;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthenticationController {
    private AuthenticationService service;


    @PostMapping("/get-token-by-username-password")
    public ResponseEntity<Token> getJwtTokenByUsernamePassword(UserAuthenticationCommand command) {
        Token token = service.getToken(command);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

    @GetMapping("/get-token-by-github-oauth")
    public ResponseEntity<Token> getJwtByGithubOauth() {
        Token token = null;
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }
}
