package com.Eragoo.Blog.auth.oauth.github;

import com.Eragoo.Blog.auth.TestTokenProviderFactory;
import com.Eragoo.Blog.security.AuthenticatedUser;
import com.Eragoo.Blog.security.TokenProvider;
import com.Eragoo.Blog.security.SecurityProps;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.web.client.RestOperations;

import java.util.Optional;

@SpringJUnitConfig()
public class GithubAuthenticationServiceTest {
    private static GithubAuthenticationService authenticationService;
    private static TokenProvider tokenProvider;
    private static String baseUrl = "https://github.com/login/oauth/authorize?";
    private static String githubTestAccessToken;
    private static String clientSecret;


    @BeforeAll
    public static void init() {
        tokenProvider = TestTokenProviderFactory.getTokenProvider();

        RestOperations restOperations = Mockito.mock(RestOperations.class);
        ResponseEntity<String> responseEntity = ResponseEntity.status(HttpStatus.OK).body(githubTestAccessToken);
        Mockito.when(restOperations.exchange("", HttpMethod.POST, null, String.class)).thenReturn(responseEntity);

        authenticationService = new GithubAuthenticationService(restOperations, tokenProvider);
    }

    @Test
    public void assertGithubAuthUrlNotNull() {
        String githubOauthUrl = authenticationService.getGithubOauthUrl();
        Assertions.assertNotNull(githubOauthUrl);
    }

    @Test
    public void assertGithubOauthUrlMatchesToGithubAuthorizeUrl() {
        String githubOauthUrl = authenticationService.getGithubOauthUrl();
        Assertions.assertTrue(githubOauthUrl.startsWith(baseUrl));
    }

    @Test
    public void usernameInTokenMatchesWithProvidedUsername() {
        String token = authenticationService.getToken("");
        AuthenticatedUser user = tokenProvider.parseUser(token).get();
        Assertions.assertEquals("test", user.getUsername());
    }
}
