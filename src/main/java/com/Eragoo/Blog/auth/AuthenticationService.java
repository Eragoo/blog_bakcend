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

@Service
@AllArgsConstructor
public class AuthenticationService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private JwtTokenProvider tokenProvider;

    public Token getToken(UserAuthenticationCommand command) {
        BlogUser user = userRepository.findByUsername(command.getUsername());
        verifyUserFromDb(command, user);
        //tokenProvider.createToken(user.getUsername(), user.getRole().getPermissions());
        return null;
    }

    private void verifyUserFromDb(UserAuthenticationCommand command, BlogUser user) {
        if (user == null || !isCredentialsCorrect(command, user)) {
            throw new UserNotFoundException("user with this username and password not found");
        }
    }

    private boolean isCredentialsCorrect(UserAuthenticationCommand command, BlogUser user) {
        return isPasswordMatches(command.getPassword(), user.getPassword())
                && user.getUsername().equals(command.getUsername());
    }

    private boolean isPasswordMatches(String rawPassword, String encodedPassword) {
        return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
    }
}
