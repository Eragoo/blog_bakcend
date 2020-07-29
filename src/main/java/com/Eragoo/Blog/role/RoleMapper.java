package com.Eragoo.Blog.role;

import com.Eragoo.Blog.role.dto.RoleCommand;
import com.Eragoo.Blog.role.dto.RoleDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface RoleMapper {
    RoleDto entityToDto(Role role);

    @Mapping(target = "id", ignore = true)
    Role commandToEntity(RoleCommand roleCommand);

    default RolePermission permissionToRolePermission(Permission permission) {
        return new RolePermission(permission);
    }
}
