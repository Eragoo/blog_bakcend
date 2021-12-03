package com.Eragoo.Blog.article.dto;

import com.Eragoo.Blog.article.Article;
import com.Eragoo.Blog.article.ArticleSpecification;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
public class ArticleFilteringCommand {
    String title;
    Set<Long> authors;
    Set<Long> genres;

    public Specification<Article> getSpecification() {
        return ArticleSpecification.filterByTitle(title)
                .and(ArticleSpecification.filterByAuthors(authors))
                .and(ArticleSpecification.filterByGenres(genres));
    }
}
