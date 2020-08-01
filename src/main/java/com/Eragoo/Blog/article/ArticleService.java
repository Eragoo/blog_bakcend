package com.Eragoo.Blog.article;

import com.Eragoo.Blog.article.dto.ArticleCommand;
import com.Eragoo.Blog.article.dto.ArticleDto;
import com.Eragoo.Blog.article.dto.ArticleSimpleDto;
import com.Eragoo.Blog.article.genre.Genre;
import com.Eragoo.Blog.article.genre.GenreRepository;
import com.Eragoo.Blog.error.exception.ConflictException;
import com.Eragoo.Blog.error.exception.NotFoundException;
import com.Eragoo.Blog.user.BlogUser;
import com.Eragoo.Blog.user.BlogUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

@Service
@AllArgsConstructor
@Transactional
public class ArticleService {
    private ArticleRepository articleRepository;
    private GenreRepository genreRepository;
    private ArticleMapper articleMapper;
    private BlogUserRepository blogUserRepository;

    public Page<ArticleSimpleDto> getAll(Pageable pageable) {
        Page<Article> articles = articleRepository.findAll(pageable);
        return articles.map(articleMapper::entityToSimpleDto);
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

        Article article = new Article();
        updateArticleFields(article, articleCommand, author, genres);
        return saveArticle(article);
    }

    private void updateArticleFields(Article article, ArticleCommand articleCommand, BlogUser author, Set<Genre> genres) {
        articleMapper.updateArticleFromCommand(articleCommand, article);
        article.setGenres(genres);
        article.setAuthor(author);
    }

    public ArticleDto update(long id , ArticleCommand articleCommand) {
        Article article = getArticleIfExist(id);
        BlogUser newAuthor = findAuthor(articleCommand.getAuthorId());
        Set<Genre> newGenres = findAllGenres(articleCommand.getGenres());
        validateProvidedGenres(articleCommand.getGenres(), newGenres);
        updateArticleFields(article, articleCommand, newAuthor, newGenres);
        return articleMapper.entityToDto(article);
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
