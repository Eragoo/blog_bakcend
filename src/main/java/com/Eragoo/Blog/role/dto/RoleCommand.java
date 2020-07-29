package com.Eragoo.Blog.role.dto;

import com.Eragoo.Blog.role.Permission;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class RoleCommand {
    private String name;
    private Set<Permission> permissions;
}
