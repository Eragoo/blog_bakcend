package com.Eragoo.Blog.user;

import com.Eragoo.Blog.user.dto.BlogUserCommand;
import com.Eragoo.Blog.user.dto.BlogUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface BlogUserMapper {
    BlogUserDto entityToDto(BlogUser blogUser);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    BlogUser commandToEntity(BlogUserCommand command);
}
