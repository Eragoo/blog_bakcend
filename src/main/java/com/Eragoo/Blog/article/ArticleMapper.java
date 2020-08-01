package com.Eragoo.Blog.article;

import com.Eragoo.Blog.article.dto.ArticleCommand;
import com.Eragoo.Blog.article.dto.ArticleDto;
import com.Eragoo.Blog.article.dto.ArticleSimpleDto;
import com.Eragoo.Blog.article.dto.AuthorDto;
import com.Eragoo.Blog.user.BlogUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Set;

@Mapper
public interface ArticleMapper {
    ArticleDto entityToDto(Article article);

    AuthorDto blogUserToAuthorDto(BlogUser user);

    ArticleSimpleDto entityToSimpleDto(Article article);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "genres", ignore = true)
    void updateArticleFromCommand(ArticleCommand command, @MappingTarget Article article);
}
