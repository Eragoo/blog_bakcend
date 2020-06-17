package com.Eragoo.Blog.security;

import com.Eragoo.Blog.role.Permission;
import lombok.Getter;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class AuthenticatedUser extends User {
    private String username;

    public AuthenticatedUser(String username, List<String> permissions) {
        super(username, "", permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet()));
        this.username = username;
    }

    public boolean hasPermission(@NonNull Permission permission) {
        return getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(authority -> authority.equals(permission.name()));
    }
}
