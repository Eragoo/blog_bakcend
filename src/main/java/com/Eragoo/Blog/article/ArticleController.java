package com.Eragoo.Blog.article;

import com.Eragoo.Blog.article.dto.ArticleCommand;
import com.Eragoo.Blog.article.dto.ArticleDto;
import com.Eragoo.Blog.article.dto.ArticleSimpleDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

@RestController
@RequestMapping("api/article")
@AllArgsConstructor
public class ArticleController {
    private ArticleService articleService;
    @GetMapping
    public ResponseEntity<Set<ArticleSimpleDto>> getArticles() {
        Set<ArticleSimpleDto> articles = articleService.getAll();
        return ResponseEntity.ok(articles);
    }

    @GetMapping("{id}")
    public ResponseEntity<ArticleDto> get(@PathVariable long id) {
        ArticleDto articleDto = articleService.get(id);
        return ResponseEntity.ok(articleDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        articleService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<ArticleDto> create(ArticleCommand article) {
        ArticleDto articleDto = articleService.create(article);
        return ResponseEntity.ok(articleDto);
    }
}
