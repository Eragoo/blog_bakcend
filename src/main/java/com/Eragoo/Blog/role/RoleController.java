package com.Eragoo.Blog.role;

import com.Eragoo.Blog.role.dto.RoleCommand;
import com.Eragoo.Blog.role.dto.RoleDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/role")
public class RoleController {
    private RoleService roleService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(T(com.Eragoo.Blog.role.Permission).MANAGE_ROLES)")
    public ResponseEntity<RoleDto> get(@PathVariable long id) {
        RoleDto role = roleService.getById(id);
        return ResponseEntity.ok(role);
    }

    @PostMapping
    @PreAuthorize("hasAuthority(T(com.Eragoo.Blog.role.Permission).MANAGE_ROLES)")
    public ResponseEntity<RoleDto> create(RoleCommand roleCommand) {
        RoleDto role = roleService.save(roleCommand);
        return new ResponseEntity<>(role, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAuthority(T(com.Eragoo.Blog.role.Permission).MANAGE_ROLES)")
    public ResponseEntity<List<RoleDto>> getAll() {
        List<RoleDto> all = roleService.getAll();
        return ResponseEntity.ok(all);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(T(com.Eragoo.Blog.role.Permission).MANAGE_ROLES)")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        roleService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority(T(com.Eragoo.Blog.role.Permission).MANAGE_ROLES)")
    public ResponseEntity<RoleDto> update(@PathVariable long id, RoleCommand roleCommand) {
        RoleDto role = roleService.update(id, roleCommand);
        return ResponseEntity.ok(role);
    }
}
