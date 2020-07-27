package com.Eragoo.Blog.security;

import com.Eragoo.Blog.error.exception.NotFoundException;
import com.Eragoo.Blog.user.BlogUser;
import com.Eragoo.Blog.user.BlogUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.Eragoo.Blog.user.BlogUserHelper.getGrantedAuthorities;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private BlogUserRepository blogUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        BlogUser blogUser = blogUserRepository.findByUsername(username);
        if (blogUser == null){
            throw new NotFoundException();
        }
        List<GrantedAuthority> authorities = getGrantedAuthorities(blogUser);
        return new User(blogUser.getUsername(), blogUser.getPassword(), authorities);
    }
}
