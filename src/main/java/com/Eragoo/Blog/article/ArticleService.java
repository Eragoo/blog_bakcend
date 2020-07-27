package com.Eragoo.Blog.article;

import com.Eragoo.Blog.article.dto.ArticleCommand;
import com.Eragoo.Blog.article.dto.ArticleDto;
import com.Eragoo.Blog.article.genre.Genre;
import com.Eragoo.Blog.article.genre.GenreRepository;
import com.Eragoo.Blog.error.exception.ConflictException;
import com.Eragoo.Blog.error.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

@Service
@AllArgsConstructor
public class ArticleService {
    private ArticleRepository articleRepository;
    private GenreRepository genreRepository;
    private ArticleMapper articleMapper;

    public Set<ArticleDto> getAll() {
        List<Article> articles = articleRepository.findAll();
        return articleMapper.entityListToSetDto(articles);
    }

    public ArticleDto get(long id) {
        Article article = getArticleIfExist(id);
        return articleMapper.entityToDto(article);
    }

    public void delete(long id) {
        Article article = getArticleIfExist(id);
        articleRepository.delete(article);
    }

    public ArticleDto create(ArticleCommand articleCommand) {
        Set<Genre> genres = findAllGenres(articleCommand);
        validateProvidedGenres(articleCommand, genres);
        Article article = constructArticle(articleCommand, genres);
        return saveArticle(article);
    }

    private ArticleDto saveArticle(Article article) {
        Article saved = articleRepository.save(article);
        return articleMapper.entityToDto(saved);
    }

    private Article constructArticle(ArticleCommand articleCommand, Set<Genre> genres) {
        Article article = articleMapper.commandToEntity(articleCommand);
        article.setGenres(genres);
        return article;
    }

    private Set<Genre> findAllGenres(ArticleCommand articleCommand) {
        return new HashSet<>(genreRepository
                .findAllById(articleCommand.getGenres()));
    }

    private void validateProvidedGenres(ArticleCommand articleCommand, Collection<Genre> genres) {
        boolean match = isGenresMatches(articleCommand.getGenres(), genres);
        if (!match) {
            throw new ConflictException("Some genre in provided article is not exist");
        }
    }

    private boolean isGenresMatches(Collection<Long> articleIds, Collection<Genre> genres) {
        return articleIds
                .stream()
                .allMatch(getGenreMatchPredicate(genres));
    }

    private Predicate<Long> getGenreMatchPredicate(Collection<Genre> genres) {
        return genreId -> genres.stream().anyMatch(genre -> genreId.equals(genre.getId()));
    }

    private Article getArticleIfExist(long id) {
        return articleRepository
                    .findById(id)
                    .orElseThrow(() -> new NotFoundException("Article with id " + id + " not found"));
    }
}
