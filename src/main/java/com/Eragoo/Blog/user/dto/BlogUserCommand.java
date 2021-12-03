package com.Eragoo.Blog.user.dto;

import com.Eragoo.Blog.role.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
public class BlogUserCommand {
    @NotBlank
    private String login;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
