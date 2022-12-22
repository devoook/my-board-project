package com.myproject.boardproject.dto.request;

import com.myproject.boardproject.dto.ArticleCommentDto;
import com.myproject.boardproject.dto.UserAccountDto;

public record ArticleCommentRequest(Long articleId, String content) {

    public static ArticleCommentRequest of(Long articleId, String content) {
        return new ArticleCommentRequest(articleId, content);
    }

    public ArticleCommentDto toDto(UserAccountDto userAccountDto) {
        return  ArticleCommentDto.of(
                 articleId,
                userAccountDto,
                content
        );
    }
}
