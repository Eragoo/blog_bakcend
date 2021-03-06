package com.Eragoo.Blog.role;

import com.Eragoo.Blog.error.exception.NotFoundException;
import com.Eragoo.Blog.role.dto.RoleCommand;
import com.Eragoo.Blog.role.dto.RoleDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Transactional
    public RoleDto save(RoleCommand roleCommand) {
        Role providedRole = roleMapper.commandToEntity(roleCommand);
        Role savedRole = roleRepository.save(providedRole);
        return roleMapper.entityToDto(savedRole);
    }

    public List<RoleDto> getAll() {
        List<Role> all = roleRepository.findAll();
        return roleMapper.listEntityToListDto(all);
    }

    public void delete(long id) {
        roleRepository.deleteById(id);
    }

    @Transactional
    public RoleDto update(long id, RoleCommand command) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Role with id " + id + " not found"));

        roleMapper.updateRoleFromCommand(command, role);
        //because of transaction entity saves without calling .save() method
        return roleMapper.entityToDto(role);
    }
}
