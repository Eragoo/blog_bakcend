package com.Eragoo.Blog.auth.oauth.github;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth/oauth/github")
@AllArgsConstructor
public class GithubAuthenticationController {
    private GithubAuthenticationService authenticationService;

    @GetMapping("/oauth-url")
    public ResponseEntity<String> getGithubOauthUrl() {
        String githubOauthUrl = authenticationService.getGithubOauthUrl();
        return ResponseEntity.ok(githubOauthUrl);
    }

    @GetMapping("/token")
    public ResponseEntity<String> getToken(@RequestParam String code) {
        String token = authenticationService.getToken(code);
        return ResponseEntity.ok(token);
    }
}
