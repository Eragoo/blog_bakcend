package com.Eragoo.Blog.auth;

import com.Eragoo.Blog.security.SecurityProps;
import com.Eragoo.Blog.security.TokenProvider;

import java.time.Duration;

public class TestTokenProviderFactory {
    private static TokenProvider tokenProvider;

    static {
        SecurityProps securityProps = new SecurityProps();
        securityProps.setLifetime(Duration.ofDays(1));
        securityProps.setSignature(SecurityTestDataProvider.SECRET);

        tokenProvider = new TokenProvider(securityProps);
    }

    public static TokenProvider getTokenProvider() {
        return tokenProvider;
    }
}
