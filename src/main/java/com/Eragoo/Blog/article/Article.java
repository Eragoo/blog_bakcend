package com.Eragoo.Blog.article;

import com.Eragoo.Blog.article.genre.Genre;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    @Id
    private long id;
    private String title;
    private String text;
    private int rating;
    @ManyToMany
    @JoinTable(
            name = "article_genres",
            joinColumns = {@JoinColumn(name = "article_id")},
            inverseJoinColumns = {@JoinColumn(name = "genre_id")}
    )
    private Set<Genre> genres;
}

