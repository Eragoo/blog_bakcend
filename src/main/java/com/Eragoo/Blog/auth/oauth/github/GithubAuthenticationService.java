package com.Eragoo.Blog.auth.oauth.github;

import com.Eragoo.Blog.security.TokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import java.util.Collections;
import java.util.Map;

import static com.Eragoo.Blog.auth.oauth.github.GithubRequestEntityHelper.getAccessTokenHttpEntity;
import static com.Eragoo.Blog.auth.oauth.github.GithubRequestEntityHelper.getGithubUserDataHttpEntity;

@Service
@AllArgsConstructor
public class GithubAuthenticationService {
    private RestOperations restOperations;
    private TokenProvider tokenProvider;
    private GithubSecurityData securityData;

    private static String baseUrl = "https://github.com/login/oauth/authorize?";
    private static String scope = "user:email";
    private static String redirectUri = "http%3A%2F%2Flocalhost%3A8080%2Flogin%2Foauth2%2Fcode%2Fgithub";
    private static String responseType = "code";
    private static String accessTokenResourceUrl = "https://github.com/login/oauth/access_token";
    private static String userGettingUrl = "https://api.github.com/user";

    public String getGithubOauthUrl() {
        return baseUrl
                + "client_id=" + securityData.getClientId()
                + "&scope=" + scope
                + "&redirect_uri=" + redirectUri
                + "&response_type=" + responseType;
    }

    public String getToken(String accessCode) {
        String accessToken = getAccessToken(accessCode);
        GithubUserData githubUserData = getGithubUserData(accessToken);
        return createToken(githubUserData.getName());
    }

    private String getAccessToken(String code) {
        return getGithubAccessTokenDto(code).getAccess_token();
    }

    private GithubAccessTokenDto getGithubAccessTokenDto(String code) {
        HttpEntity<Map<String, String>> requestEntity = getAccessTokenHttpEntity(code,
                securityData.getClientId(),
                securityData.getSecret());

        ResponseEntity<GithubAccessTokenDto> responseEntity = restOperations.exchange(accessTokenResourceUrl,
                HttpMethod.POST,
                requestEntity,
                GithubAccessTokenDto.class);

        return responseEntity.getBody();
    }

    private GithubUserData getGithubUserData(String accessToken) {
        HttpEntity<Map<String, String>> httpEntity = getGithubUserDataHttpEntity(accessToken);
        ResponseEntity<GithubUserData> responseEntity = restOperations.exchange(userGettingUrl,
                HttpMethod.GET,
                httpEntity,
                GithubUserData.class);

        return responseEntity.getBody();
    }

    private String createToken(String username) {
        return tokenProvider.createToken(username, Collections.emptyList());
    }
}
