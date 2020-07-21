package com.Eragoo.Blog.article;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/article")
@AllArgsConstructor
public class ArticleController {
    private ArticleService articleService;
    @GetMapping
    public ResponseEntity<List<Article>> getArticles() {
        List<Article> articles = articleService.getAll();
        return ResponseEntity.ok(articles);
    }
}
