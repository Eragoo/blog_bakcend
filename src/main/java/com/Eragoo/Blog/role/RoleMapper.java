package com.Eragoo.Blog.role;

import com.Eragoo.Blog.role.dto.RoleCommand;
import com.Eragoo.Blog.role.dto.RoleDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper
public interface RoleMapper {
    RoleDto entityToDto(Role role);

    @Mapping(target = "id", ignore = true)
    Role commandToEntity(RoleCommand roleCommand);


    List<RoleDto> listEntityToListDto(List<Role> roles);

    @Mapping(target = "id", ignore = true)
    void updateRoleFromCommand(RoleCommand command, @MappingTarget Role role);

    default RolePermission permissionToRolePermission(Permission permission) {
        return new RolePermission(permission);
    }
}
