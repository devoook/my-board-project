package com.myproject.boardproject.dto.request;

import com.myproject.boardproject.dto.ArticleDto;
import com.myproject.boardproject.dto.UserAccountDto;

public record ArticleRequest (
        String title,
        String content,
        String hashtag
) {

    public static ArticleRequest of (String title, String content, String hashtag) {
        return new ArticleRequest(title, content, hashtag);
    }
    // ArticleRequest + 회원 정보 Dto => ArticleDto
    public ArticleDto toDto(UserAccountDto userAccountDto) {
        return ArticleDto.of(
                userAccountDto,
                title,
                content,
                hashtag
        );
    }


}
