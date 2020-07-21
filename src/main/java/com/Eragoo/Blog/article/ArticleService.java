package com.Eragoo.Blog.article;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ArticleService {
    private ArticleRepository articleRepository;

    public List<Article> getAll() {
        return articleRepository.findAll();
    }
}
