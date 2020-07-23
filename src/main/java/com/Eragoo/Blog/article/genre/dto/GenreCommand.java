package com.Eragoo.Blog.article.genre.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
public class GenreCommand {
    @NotBlank
    private String name;
}
