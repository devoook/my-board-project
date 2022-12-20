package com.myproject.boardproject.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
// java 17의 record 활용
public record ArticleDto(LocalDateTime createdAt,
                         String createdBy,
                         String title,
                         String content,
                         String hashtag)
         {
    public static ArticleDto of(LocalDateTime createdAt, String createdBy, String title, String content, String hashtag) {
        return new ArticleDto(createdAt, createdBy, title, content, hashtag);
    }
}
