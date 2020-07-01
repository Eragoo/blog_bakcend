package com.Eragoo.Blog.auth.oauth.github;

import com.Eragoo.Blog.security.TokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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
        Map<String, String> params = new HashMap<>();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "token " + accessToken);

        HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(params, httpHeaders);

        ResponseEntity<GithubUserData> exchange = restOperations.exchange(userGettingUrl, HttpMethod.GET, httpEntity, GithubUserData.class);

        String token = tokenProvider.createToken(exchange.getBody().getLogin(), Collections.emptyList());

        return token;
    }

    private String getAccessToken(String code) {
        Map<String, String> params = new HashMap<>();
        params.put("client_id", clientId);
        params.put("client_secret", secret);
        params.put("code", code);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(params, httpHeaders);

        ResponseEntity<GithubAccessTokenDto> responseEntity = restOperations.exchange(accessTokenResourceUrl,
                HttpMethod.POST,
                requestEntity,
                GithubAccessTokenDto.class);
        return responseEntity.getBody().getAccess_token();
    }
}
