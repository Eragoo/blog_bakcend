package com.Eragoo.Blog.article.genre.dto;

import com.Eragoo.Blog.article.genre.Genre;
import com.Eragoo.Blog.article.genre.GenreSpecification;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

@Getter
@Setter
@NoArgsConstructor
public class GenreFilteringCommand {
    String name;

    public Specification<Genre> getSpecification() {
        return GenreSpecification.getFilteredByName(name);
    }
}
