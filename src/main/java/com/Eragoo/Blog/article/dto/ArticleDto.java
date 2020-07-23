package com.Eragoo.Blog.article.dto;

import com.Eragoo.Blog.article.genre.dto.GenreDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
public class ArticleDto {
    private long id;
    private String title;
    private String text;
    private int rating;
    private Set<GenreDto> genres;
}
