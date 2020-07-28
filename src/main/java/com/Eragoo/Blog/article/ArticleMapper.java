package com.Eragoo.Blog.article;

import com.Eragoo.Blog.article.dto.ArticleCommand;
import com.Eragoo.Blog.article.dto.ArticleDto;
import com.Eragoo.Blog.article.dto.ArticleSimpleDto;
import com.Eragoo.Blog.article.dto.AuthorDto;
import com.Eragoo.Blog.user.BlogUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

@Mapper
public interface ArticleMapper {
    ArticleDto entityToDto(Article article);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "genres", ignore = true)
    Article commandToEntity(ArticleCommand command);

    Set<ArticleSimpleDto> entityListToSetDto(List<Article> articleList);

    AuthorDto blogUserToAuthorDto(BlogUser user);
}
