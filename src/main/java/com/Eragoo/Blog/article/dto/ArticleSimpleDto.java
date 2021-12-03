package com.Eragoo.Blog.article.dto;

import com.Eragoo.Blog.article.genre.dto.GenreDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
public class ArticleSimpleDto {
    private long id;
    private String title;
    private int rating;
    private AuthorDto author;
    private Set<GenreDto> genres;
}
