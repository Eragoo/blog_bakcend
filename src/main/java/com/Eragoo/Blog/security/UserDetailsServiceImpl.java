package com.Eragoo.Blog.security;

import com.Eragoo.Blog.exception.UserNotFoundException;
import com.Eragoo.Blog.user.BlogUser;
import com.Eragoo.Blog.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        BlogUser blogUser = userRepository.findByUsername(username);
        if (blogUser == null){
            throw new UserNotFoundException();
        }
        List<GrantedAuthority> authorities = blogUser.getRole().getPermissions()
                .stream()
                .map(rolePermission -> new SimpleGrantedAuthority(rolePermission.getPermission().name()))
                .collect(Collectors.toList());


        return new User(blogUser.getUsername(), blogUser.getPassword(), authorities);
    }
}
