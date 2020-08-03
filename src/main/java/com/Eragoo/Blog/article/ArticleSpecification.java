package com.Eragoo.Blog.article;

import com.Eragoo.Blog.article.genre.Genre_;
import com.Eragoo.Blog.user.BlogUser_;
import org.springframework.data.jpa.domain.Specification;

import java.util.Set;

public class ArticleSpecification {
    public static Specification<Article> filterByTitle(String title) {
        return (r, cq, cb) -> title == null ?
                null : cb.like(cb.upper(r.get(Article_.TITLE)), title.toUpperCase() + "%");
    }

    public static Specification<Article> filterByAuthors(Set<Long> authorIds) {
        return (r, cq, cb) -> authorIds == null || authorIds.isEmpty() ?
                null : r.join(Article_.AUTHOR).get(BlogUser_.ID).in(authorIds);
    }

    public static Specification<Article> filterByGenres(Set<Long> genres) {
        return (r, cq, cb) -> genres == null || genres.isEmpty() ?
                null : r.join(Article_.GENRES).get(Genre_.ID).in(genres);
    }
}
