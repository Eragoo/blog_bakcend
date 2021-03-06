package com.Eragoo.Blog.article;

import com.Eragoo.Blog.article.dto.ArticleCommand;
import com.Eragoo.Blog.article.dto.ArticleDto;
import com.Eragoo.Blog.article.dto.ArticleFilteringCommand;
import com.Eragoo.Blog.article.dto.ArticleSimpleDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("api/article")
@AllArgsConstructor
public class ArticleController {
    private ArticleService articleService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority(T(com.Eragoo.Blog.role.Permission).VIEW_ARTICLES, " +
            "T(com.Eragoo.Blog.role.Permission).MANAGE_ARTICLES)")
    public ResponseEntity<Page<ArticleSimpleDto>> getAll(Pageable pageable, ArticleFilteringCommand filteringCommand) {
        Page<ArticleSimpleDto> articles = articleService.getAll(pageable, filteringCommand);
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority(T(com.Eragoo.Blog.role.Permission).VIEW_ARTICLES, " +
            "T(com.Eragoo.Blog.role.Permission).MANAGE_ARTICLES)")
    public ResponseEntity<ArticleDto> get(@PathVariable long id) {
        ArticleDto articleDto = articleService.get(id);
        return ResponseEntity.ok(articleDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(T(com.Eragoo.Blog.role.Permission).MANAGE_ARTICLES) ||" +
            "(hasAuthority(T(com.Eragoo.Blog.role.Permission).MANAGE_OWN_ARTICLE) &&" +
            "@articleMethodSecurityHelper.isOwnArticle(principal, #id))")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        articleService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    @PreAuthorize("hasAuthority(T(com.Eragoo.Blog.role.Permission).CREATE_ARTICLE)")
    public ResponseEntity<ArticleDto> create(ArticleCommand article) {
        ArticleDto articleDto = articleService.create(article);
        return new ResponseEntity<>(articleDto,  HttpStatus.CREATED);
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority(T(com.Eragoo.Blog.role.Permission).MANAGE_ARTICLES) ||" +
            "(hasAuthority(T(com.Eragoo.Blog.role.Permission).MANAGE_OWN_ARTICLE) &&" +
            "@articleMethodSecurityHelper.isOwnArticle(principal, #id))")
    public ResponseEntity<ArticleDto> update(@P("id") @PathVariable long id, ArticleCommand article) {
        ArticleDto articleDto = articleService.update(id, article);
        return ResponseEntity.ok(articleDto);
    }
}
