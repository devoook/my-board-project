package com.myproject.boardproject.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

// java 17의 record 활용
public record ArticleUpdateDto(
        String title,
        String content,
        String hashtag)
         {
    public static ArticleUpdateDto of(String title, String content, String hashtag) {
        return new ArticleUpdateDto(title, content, hashtag);
    }
}
