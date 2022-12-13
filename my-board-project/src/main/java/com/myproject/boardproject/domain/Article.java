package com.myproject.boardproject.domain;

import java.time.LocalDate;

public class Article {

    private Long id;
    private String title;
    private String content;
    private String hashtag;

    private LocalDate createdAt;
    private String createdBy;
    private LocalDate modifiedAt;
    private String modifiedBy;

}
