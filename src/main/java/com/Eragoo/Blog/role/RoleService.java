package com.Eragoo.Blog.role;

import com.Eragoo.Blog.error.exception.NotFoundException;
import com.Eragoo.Blog.role.dto.RoleCommand;
import com.Eragoo.Blog.role.dto.RoleDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleService {
    private RoleRepository roleRepository;
    private RoleMapper roleMapper;

    public RoleDto getById(long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Role with id " + id + " not found"));

        return roleMapper.entityToDto(role);
    }

    public RoleDto save(RoleCommand roleCommand) {
        Role providedRole = roleMapper.commandToEntity(roleCommand);
        Role savedRole = roleRepository.save(providedRole);
        return roleMapper.entityToDto(savedRole);
    }
}
