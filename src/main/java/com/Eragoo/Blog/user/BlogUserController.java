package com.Eragoo.Blog.user;

import com.Eragoo.Blog.security.AuthenticatedUser;
import com.Eragoo.Blog.user.dto.BlogUserCommand;
import com.Eragoo.Blog.user.dto.BlogUserDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class BlogUserController {
    private BlogUserService blogUserService;

    @GetMapping("/current")
    @PreAuthorize("hasAuthority(T(com.Eragoo.Blog.role.Permission).GET_CURRENT_USER)")
    public ResponseEntity<AuthenticatedUser> getCurrentUser(@AuthenticationPrincipal AuthenticatedUser user) {
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PostMapping("/create")
    public ResponseEntity<BlogUserDto> create(BlogUserCommand blogUserCommand) {
        BlogUserDto blogUserDto = blogUserService.create(blogUserCommand);
        return new ResponseEntity<>(blogUserDto, HttpStatus.CREATED);
    }
}
