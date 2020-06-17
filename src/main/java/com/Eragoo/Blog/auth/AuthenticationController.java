package com.Eragoo.Blog.auth;

import com.Eragoo.Blog.auth.dto.Token;
import com.Eragoo.Blog.security.UserAuthenticationCommand;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthenticationController {
    private AuthenticationService service;


    @PostMapping("/username-password-token")
    public ResponseEntity<Token> getJwtTokenByUsernamePassword(UserAuthenticationCommand command) {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
