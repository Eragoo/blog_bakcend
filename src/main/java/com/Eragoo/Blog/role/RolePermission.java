package com.Eragoo.Blog.role;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Embeddable
@Table(name = "role_permission")
@Getter
@Setter
public class RolePermission {
    @Enumerated(EnumType.STRING)
    private Permission permission;
}
