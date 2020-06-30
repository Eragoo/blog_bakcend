package com.Eragoo.Blog.auth;

import com.Eragoo.Blog.auth.dto.Token;
import com.Eragoo.Blog.exception.UserNotFoundException;
import com.Eragoo.Blog.security.JwtTokenProvider;
import com.Eragoo.Blog.security.UserAuthenticationCommand;
import com.Eragoo.Blog.user.BlogUser;
import com.Eragoo.Blog.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.Eragoo.Blog.user.BlogUserHelper.getPermissions;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private JwtTokenProvider tokenProvider;

    public Token getToken(UserAuthenticationCommand command) {
        BlogUser user = userRepository.findByUsername(command.getUsername());
        verifyUsernameAndPassword(command, user);
        List<String> permissions = getPermissions(user);
        String providedToken = tokenProvider.createToken(user.getUsername(), permissions);
        return new Token(providedToken);
    }

    private void verifyUsernameAndPassword(UserAuthenticationCommand expectedUser, BlogUser receivedUser) {
        if (receivedUser == null || !isPasswordMatches(expectedUser.getPassword(), receivedUser.getPassword())) {
            throw new UserNotFoundException("user with this username and password not found");
        }
    }

    private boolean isPasswordMatches(String expectedEncodedPassword, String receivedRawPassword) {
        assert expectedEncodedPassword != null && receivedRawPassword != null;
        return bCryptPasswordEncoder.matches(expectedEncodedPassword, receivedRawPassword);
    }
}
