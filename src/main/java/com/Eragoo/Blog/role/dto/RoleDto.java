package com.Eragoo.Blog.role.dto;

import com.Eragoo.Blog.role.RolePermission;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
public class RoleDto {
    private long id;
    private String name;
    private Set<RolePermission> permissions;
}
