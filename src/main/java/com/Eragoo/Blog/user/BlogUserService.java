package com.Eragoo.Blog.user;

import com.Eragoo.Blog.error.exception.ConflictException;
import com.Eragoo.Blog.role.Role;
import com.Eragoo.Blog.role.RoleController;
import com.Eragoo.Blog.role.RoleRepository;
import com.Eragoo.Blog.user.dto.BlogUserCommand;
import com.Eragoo.Blog.user.dto.BlogUserDto;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BlogUserService {
    public static final String DEFAULT_USER_ROLE = "REGULAR_USER";

    private BlogUserRepository blogUserRepository;
    //private BlogUserMapper blogUserMapper;
    private BCryptPasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;

    public BlogUserDto create(BlogUserCommand blogUserCommand) {
        BlogUser blogUser = createBlogUserUsingCommand(blogUserCommand);
        BlogUser savedUser = blogUserRepository.save(blogUser);
        //return blogUserMapper.entityToDto(savedUser);
        return null;
    }

    private BlogUser createBlogUserUsingCommand(BlogUserCommand blogUserCommand) {
//        BlogUser blogUser = blogUserMapper.commandToEntity(blogUserCommand);
//        setBlogUserDefaultRole(blogUser);
//        String encodedPassword = passwordEncoder.encode(blogUserCommand.getPassword());
//        blogUser.setPassword(encodedPassword);
//        return blogUser;
        return null;
    }

    private void setBlogUserDefaultRole(BlogUser blogUser) {
        Role defaultRole = roleRepository.findByName(DEFAULT_USER_ROLE)
                .orElseThrow(() -> new ConflictException("Role with name " + DEFAULT_USER_ROLE + " not exist"));

        blogUser.setRole(defaultRole);
    }
}
