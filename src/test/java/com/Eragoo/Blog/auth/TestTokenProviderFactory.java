package com.Eragoo.Blog.auth;

import com.Eragoo.Blog.security.SecurityProps;
import com.Eragoo.Blog.security.TokenProvider;

import java.time.Duration;

public class TestTokenProviderFactory {
    private static TokenProvider tokenProvider;
    private static String secret = "blog-secret-very-very-very-secret-key-that-you-dont-know-aertthrtrwetethrewfg-dstdhgsdfa";

    static {
        SecurityProps securityProps = new SecurityProps();
        securityProps.setLifetime(Duration.ofDays(1));
        securityProps.setSignature(secret);

        tokenProvider = new TokenProvider(securityProps);
    }

    public static TokenProvider getTokenProvider() {
        return tokenProvider;
    }
}
