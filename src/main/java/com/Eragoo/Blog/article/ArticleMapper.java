package com.Eragoo.Blog.article;

import com.Eragoo.Blog.article.dto.ArticleCommand;
import com.Eragoo.Blog.article.dto.ArticleDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

@Mapper
public interface ArticleMapper {
    ArticleDto entityToDto(Article article);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "genres", ignore = true)
    Article commandToEntity(ArticleCommand command);
    Set<ArticleDto> entityListToSetDto(List<Article> articleList);
}
