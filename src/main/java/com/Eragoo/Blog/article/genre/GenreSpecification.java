package com.Eragoo.Blog.article.genre;

import org.springframework.data.jpa.domain.Specification;

public class GenreSpecification {
    public static Specification<Genre> getFilteredByName(String name) {
        return (r, cq, cb) -> name == null ?
                null : cb.like(cb.upper(r.get(Genre_.NAME)), name.toUpperCase() + "%");
    }
}
