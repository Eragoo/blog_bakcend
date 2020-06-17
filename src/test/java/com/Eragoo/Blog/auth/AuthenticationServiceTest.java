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
import java.util.stream.Collectors;

@SpringJUnitConfig()
public class AuthenticationServiceTest {
    private final static String TEST_USERNAME = "test";
    private final static String TEST_PASSWORD = "test";
    private static AuthenticationService authenticationService;
    private static JwtTokenProvider tokenProvider;
    private static BlogUser blogUser;

    @BeforeAll
    public static void init() {
        SecurityProps securityProps = createTestSecurityProps();
        tokenProvider = new JwtTokenProvider(securityProps);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        blogUser = createTestBlogUser(passwordEncoder);

        UserRepository userRepository = Mockito.mock(UserRepository.class);
        Mockito.when(userRepository.findByUsername(TEST_USERNAME)).thenReturn(blogUser);

        authenticationService = new AuthenticationService(userRepository, passwordEncoder, tokenProvider);
    }

    @Test
    public void tokenStartsBearer() {
        Token token = authenticationService.getToken(new UserAuthenticationCommand(TEST_USERNAME, TEST_PASSWORD));
        Assertions.assertTrue(token.getToken().startsWith(JwtDataProvider.AUTH_TOKEN_PREFIX));
    }

    @Test
    public void usernameInTokenMatchesWithProvidedUsername() {
        Token token = authenticationService.getToken(new UserAuthenticationCommand(TEST_USERNAME, TEST_PASSWORD));
        AuthenticatedUser authenticatedUser = tokenProvider.parseUser(token.getToken()).get();
        Assertions.assertEquals(TEST_USERNAME, authenticatedUser.getUsername());
    }

    @Test
    public void tokenGeneratesCorrectly() {
        Token token = authenticationService.getToken(new UserAuthenticationCommand(TEST_USERNAME, TEST_PASSWORD));
        List<String> permissions = getPermissions();
        String providedToken = tokenProvider.createToken(blogUser.getUsername(), permissions);
        Assertions.assertEquals(token.getToken(), providedToken);
    }

    private static List<String> getPermissions() {
        return blogUser.getRole().getPermissions().stream().map(Enum::name).collect(Collectors.toList());
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

    private static SecurityProps createTestSecurityProps() {
        SecurityProps securityProps = new SecurityProps();
        securityProps.setLifetime(Duration.ofDays(1));
        securityProps.setSignature("blog-secret-very-very-very-secret-key-that-you-dont-know-aertthrtrwetethrewfg-dstdhgsdfa");
        return securityProps;
    }
}
