package com.Eragoo.Blog.article;

import com.Eragoo.Blog.article.dto.ArticleCommand;
import com.Eragoo.Blog.article.dto.ArticleDto;
import com.Eragoo.Blog.article.dto.ArticleSimpleDto;
import com.Eragoo.Blog.article.dto.AuthorDto;
import com.Eragoo.Blog.article.genre.Genre;
import com.Eragoo.Blog.article.genre.GenreRepository;
import com.Eragoo.Blog.error.exception.ConflictException;
import com.Eragoo.Blog.error.exception.NotFoundException;
import com.Eragoo.Blog.user.BlogUser;
import com.Eragoo.Blog.user.BlogUserRepository;
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
    private BlogUserRepository blogUserRepository;

    public Set<ArticleSimpleDto> getAll() {
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
        BlogUser author = findAuthor(articleCommand.getAuthorId());
        Set<Genre> genres = findAllGenres(articleCommand.getGenres());
        validateProvidedGenres(articleCommand.getGenres(), genres);
        Article article = constructArticle(articleCommand, genres, author);
        return saveArticle(article);
    }

    private BlogUser findAuthor(Long authorId) {
        String exceptionMsg = "Author with id " + authorId + "not found";
        return blogUserRepository.findById(authorId)
                .orElseThrow(()->new ConflictException(exceptionMsg));
    }

    private ArticleDto saveArticle(Article article) {
        Article saved = articleRepository.save(article);
        return articleMapper.entityToDto(saved);
    }

    private Article constructArticle(ArticleCommand articleCommand, Set<Genre> genres, BlogUser author) {
        Article article = articleMapper.commandToEntity(articleCommand);
        article.setGenres(genres);
        article.setAuthor(author);
        return article;
    }

    private Set<Genre> findAllGenres(Set<Long> genres) {
        return new HashSet<>(genreRepository
                .findAllById(genres));
    }

    private void validateProvidedGenres(Set<Long> expected, Collection<Genre> actual) {
        boolean match = isGenresMatches(expected, actual);
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
