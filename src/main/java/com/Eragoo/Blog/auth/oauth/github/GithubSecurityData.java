package com.Eragoo.Blog.auth.oauth.github;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GithubSecurityData {
    @Value("${application.security.github.secret}")
    private String secret;
    @Value("${application.security.github.client-id}")
    private String clientId;
}
