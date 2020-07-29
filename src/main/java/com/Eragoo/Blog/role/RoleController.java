package com.Eragoo.Blog.role;

import com.Eragoo.Blog.role.dto.RoleCommand;
import com.Eragoo.Blog.role.dto.RoleDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("api/role")
public class RoleController {
    private RoleService roleService;

    @GetMapping("{id}")
    public ResponseEntity<RoleDto> get(@PathVariable long id) {
        RoleDto role = roleService.getById(id);
        return ResponseEntity.ok(role);
    }

    @PostMapping
    public ResponseEntity<RoleDto> create(RoleCommand roleCommand) {
        RoleDto role = roleService.save(roleCommand);
        return new ResponseEntity<>(role, HttpStatus.CREATED);
    }
}
