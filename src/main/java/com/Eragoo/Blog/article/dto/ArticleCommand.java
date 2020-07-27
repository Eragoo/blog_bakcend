package com.Eragoo.Blog.article.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
public class ArticleCommand {
    @NotBlank
    private String title;
    @NotBlank
    private String text;
    @NotNull
    private int rating;
    @NotNull
    private long authorId;
    @NotNull
    private Set<Long> genres;
}
