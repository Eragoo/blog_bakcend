package com.Eragoo.Blog.article;

import com.Eragoo.Blog.article.dto.ArticleDto;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ArticleMethodSecurityHelper {
    private ArticleService articleService;

    public boolean isOwnArticle(@NonNull UserDetails principal, @NonNull Long articleId) {
        ArticleDto articleDto = articleService.get(articleId);
        String articleAuthorUsername = articleDto.getAuthor().getUsername();
        return principal.getUsername().equals(articleAuthorUsername);
    }
}
