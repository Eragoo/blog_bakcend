package com.Eragoo.Blog.article;

import com.Eragoo.Blog.article.genre.Genre;
import com.Eragoo.Blog.user.BlogUser;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String text;
    private int rating;
    @ManyToOne
    private BlogUser author;
    @ManyToMany
    @JoinTable(
            name = "article_genres",
            joinColumns = {@JoinColumn(name = "article_id")},
            inverseJoinColumns = {@JoinColumn(name = "genre_id")}
    )
    private Set<Genre> genres;
}

