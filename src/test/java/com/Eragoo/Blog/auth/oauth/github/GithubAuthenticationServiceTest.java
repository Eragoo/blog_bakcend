package com.Eragoo.Blog.auth.oauth.github;

import com.Eragoo.Blog.auth.TestTokenProviderFactory;
import com.Eragoo.Blog.security.AuthenticatedUser;
import com.Eragoo.Blog.security.TokenProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.web.client.RestOperations;

import static com.Eragoo.Blog.auth.SecurityTestDataProvider.*;
import static com.Eragoo.Blog.auth.oauth.github.GithubRequestEntityHelper.getAccessTokenHttpEntity;
import static com.Eragoo.Blog.auth.oauth.github.GithubRequestEntityHelper.getGithubUserDataHttpEntity;

@SpringJUnitConfig()
public class GithubAuthenticationServiceTest {
    private static GithubAuthenticationService authenticationService;
    private static TokenProvider tokenProvider;

    private static String baseUrl = "https://github.com/login/oauth/authorize?";
    private static String accessTokenResourceUrl = "https://github.com/login/oauth/access_token";
    private static String githubUserGettingUrl = "https://api.github.com/user";
    private static final String ACCESS_CODE = "";

    @BeforeAll
    public static void init() {
        GithubSecurityData securityData = new GithubSecurityData(SECRET, CLIENT_ID);
        tokenProvider = TestTokenProviderFactory.getTokenProvider();

        RestOperations restOperations = Mockito.mock(RestOperations.class);
        specifyRestOperationMockBehaviourByAccessTokenRequest(restOperations);
        specifyRestOperationMockBehaviourByUserDataRequest(restOperations);

        authenticationService = new GithubAuthenticationService(restOperations, tokenProvider, securityData);
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
    public void usernameInGeneratedTokenMatchesWithProvidedUsername() {
        String token = authenticationService.getToken(ACCESS_CODE);
        AuthenticatedUser user = tokenProvider.parseUser(token).get();
        Assertions.assertEquals(TEST_USERNAME, user.getUsername());
    }

    private static void specifyRestOperationMockBehaviourByUserDataRequest(RestOperations restOperations) {
        GithubUserData userData = new GithubUserData(TEST_USERNAME);
        ResponseEntity<GithubUserData> userDataResponseEntity = ResponseEntity.ok(userData);
        Mockito.when(restOperations.exchange(
                            githubUserGettingUrl,
                            HttpMethod.GET,
                            getGithubUserDataHttpEntity("test-token"),
                            GithubUserData.class)
                    )
                .thenReturn(userDataResponseEntity);
    }

    private static void specifyRestOperationMockBehaviourByAccessTokenRequest(RestOperations restOperations) {
        GithubAccessTokenDto accessTokenDto = new GithubAccessTokenDto("test-token", "username", "bearer");
        ResponseEntity<GithubAccessTokenDto> tokenResponseEntity = ResponseEntity.ok(accessTokenDto);
        Mockito.when(restOperations.exchange(
                            accessTokenResourceUrl,
                            HttpMethod.POST,
                            getAccessTokenHttpEntity(ACCESS_CODE, CLIENT_ID, SECRET),
                            GithubAccessTokenDto.class)
                    )
                .thenReturn(tokenResponseEntity);
    }
}
