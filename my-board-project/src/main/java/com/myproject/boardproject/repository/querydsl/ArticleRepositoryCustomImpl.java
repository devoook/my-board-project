package com.myproject.boardproject.repository.querydsl;

import com.myproject.boardproject.domain.Article;
import com.myproject.boardproject.domain.QArticle;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class ArticleRepositoryCustomImpl extends QuerydslRepositorySupport implements ArticleRepositoryCustom{

    public ArticleRepositoryCustomImpl() {
        super(Article.class); // Article 도메인을 사용하기 때문
    }

    @Override
    public List<String> findAllDistinctHashtags() {
        QArticle article = QArticle.article;

        return from(article)
                .distinct()
                .select(article.hashtag)
                .where(article.hashtag.isNotNull())
                .fetch();
    }
}
