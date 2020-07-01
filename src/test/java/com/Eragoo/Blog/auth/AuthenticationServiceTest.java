package com.Eragoo.Blog.auth;

import com.Eragoo.Blog.auth.dto.Token;
import com.Eragoo.Blog.role.Role;
import com.Eragoo.Blog.security.*;
import com.Eragoo.Blog.user.BlogUser;
import com.Eragoo.Blog.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

import static com.Eragoo.Blog.user.BlogUserHelper.getPermissions;

@SpringJUnitConfig()
public class AuthenticationServiceTest {
    private final static String TEST_USERNAME = "test";
    private final static String TEST_PASSWORD = "test";
    private static UsernamePasswordAuthenticationService authenticationService;
    private static TokenProvider tokenProvider;
    private static BlogUser blogUser;

    @BeforeAll
    public static void init() {
        tokenProvider = TestTokenProviderFactory.getTokenProvider();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        blogUser = createTestBlogUser(passwordEncoder);

        UserRepository userRepository = Mockito.mock(UserRepository.class);
        Mockito.when(userRepository.findByUsername(TEST_USERNAME)).thenReturn(blogUser);

        authenticationService = new UsernamePasswordAuthenticationService(userRepository, passwordEncoder, tokenProvider);
    }

    @Test
    public void usernameInTokenMatchesWithProvidedUsername() {
        Token token = authenticationService.getToken(new UserAuthenticationCommand(TEST_USERNAME, TEST_PASSWORD));
        AuthenticatedUser authenticatedUser = tokenProvider.parseUser(token.getToken()).get();
        Assertions.assertEquals(TEST_USERNAME, authenticatedUser.getUsername());
    }

    @Test
    public void tokenGeneratedByAuthServiceMatchesWithTokenGeneratedByTokenProvider() {
        Token token = authenticationService.getToken(new UserAuthenticationCommand(TEST_USERNAME, TEST_PASSWORD));
        List<String> permissions = getPermissions(blogUser);
        String providedToken = tokenProvider.createToken(blogUser.getUsername(), permissions);

        String username = getUsernameFromToken(token.getToken());
        String expectedUsername = getUsernameFromToken(providedToken);

        Assertions.assertEquals(username, expectedUsername);
    }

    private String getUsernameFromToken(String token) {
        return tokenProvider.parseUser(token).get().getUsername();
    }

    private static BlogUser createTestBlogUser(BCryptPasswordEncoder passwordEncoder) {
        Role role = Mockito.mock(Role.class);
        Mockito.when(role.getPermissions()).thenReturn(Collections.emptySet());

        BlogUser blogUser = new BlogUser();
        blogUser.setUsername(TEST_USERNAME);
        blogUser.setPassword(passwordEncoder.encode(TEST_PASSWORD));
        blogUser.setRole(role);
        return blogUser;
    }
}
