package com.Eragoo.Blog.role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Embeddable
@Table(name = "role_permission")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RolePermission {
    @Enumerated(EnumType.STRING)
    private Permission permission;
}
