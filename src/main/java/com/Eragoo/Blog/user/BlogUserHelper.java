package com.Eragoo.Blog.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

public class BlogUserHelper {
    public static List<String> getPermissions(BlogUser user) {
        assert user.getRole().getPermissions() != null;
        return user.getRole().getPermissions()
                .stream()
                .map(rolePermission -> rolePermission.getPermission().name())
                .collect(Collectors.toList());
    }

    public static List<GrantedAuthority> getGrantedAuthorities(BlogUser blogUser) {
        assert blogUser.getRole().getPermissions() != null;
        return blogUser.getRole().getPermissions()
                .stream()
                .map(rolePermission -> new SimpleGrantedAuthority(rolePermission.getPermission().name()))
                .collect(Collectors.toList());
    }
}
