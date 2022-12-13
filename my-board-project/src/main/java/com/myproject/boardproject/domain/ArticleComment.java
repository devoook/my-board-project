package com.myproject.boardproject.domain;

import java.time.LocalDate;

public class ArticleComment {
    private Long id;
    private Article article;
    private String hashtag;

    private LocalDate createdAt;
    private String createdBy;
    private LocalDate modifiedAt;
    private String modifiedBy;

}
