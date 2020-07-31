package com.Eragoo.Blog.role.dto;

import com.Eragoo.Blog.role.Permission;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class RoleCommand {
    @NotBlank
    private String name;
    @NotEmpty
    private Set<Permission> permissions;
}
