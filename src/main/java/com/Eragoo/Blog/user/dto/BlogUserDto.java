package com.Eragoo.Blog.user.dto;

import com.Eragoo.Blog.role.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class BlogUserDto {
    private long id;
    private String login;
    private String username;
    private Role role;
}
