package com.Eragoo.Blog.auth.oauth.github;

import com.Eragoo.Blog.security.TokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class GithubAuthenticationService {
    private RestOperations restOperations;
    private TokenProvider tokenProvider;

    private static String secret = "7c68e6c79d953b632e4ea7479966921a3608a2c7";
    private static String clientId = "a8c13358da0525d4da5b";
    private static String baseUrl = "https://github.com/login/oauth/authorize?";
    private static String scope = "user:email";
    private static String redirectUri = "http%3A%2F%2Flocalhost%3A8080%2Flogin%2Foauth2%2Fcode%2Fgithub";
    private static String responseType = "code";
    private static String accessTokenResourceUrl = "https://github.com/login/oauth/access_token";
    private static String userGettingUrl = "https://api.github.com/user";

    public String getGithubOauthUrl() {
        return baseUrl
                + "client_id=" + clientId
                + "&scope=" + scope
                + "&redirect_uri=" + redirectUri
                + "&response_type=" + responseType;
    }

    public String getToken(String code) {
        String accessToken = getAccessToken(code);
        GithubUserData githubUserData = getGithubUserData(accessToken);
        return createToken(githubUserData.getName());
    }

    private String getAccessToken(String code) {
        return getGithubAccessTokenDto(code).getAccess_token();
    }

    private GithubAccessTokenDto getGithubAccessTokenDto(String code) {
        HttpEntity<Map<String, String>> requestEntity = getAccessTokenHttpEntity(code);
        ResponseEntity<GithubAccessTokenDto> responseEntity = restOperations.exchange(accessTokenResourceUrl,
                HttpMethod.POST,
                requestEntity,
                GithubAccessTokenDto.class);

        return responseEntity.getBody();
    }

    private HttpEntity<Map<String, String>> getAccessTokenHttpEntity(String code) {
        Map<String, String> params = getAccessTokenRequestParams(code);
        HttpHeaders httpHeaders = getAccessTokenHttpHeaders();

        return new HttpEntity<>(params, httpHeaders);
    }

    private HttpHeaders getAccessTokenHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
        return httpHeaders;
    }

    private Map<String, String> getAccessTokenRequestParams(String code) {
        Map<String, String> params = new HashMap<>();
        params.put("client_id", clientId);
        params.put("client_secret", secret);
        params.put("code", code);
        return params;
    }

    private GithubUserData getGithubUserData(String accessToken) {
        HttpEntity<Map<String, String>> httpEntity = getGithubUserDataHttpEntity(accessToken);
        ResponseEntity<GithubUserData> responseEntity = restOperations.exchange(userGettingUrl,
                HttpMethod.GET,
                httpEntity,
                GithubUserData.class);

        return responseEntity.getBody();
    }

    private HttpEntity<Map<String, String>> getGithubUserDataHttpEntity(String accessToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "token " + accessToken);
        return new HttpEntity<>(Map.of(), httpHeaders);
    }

    private String createToken(String username) {
        return tokenProvider.createToken(username, Collections.emptyList());
    }
}
