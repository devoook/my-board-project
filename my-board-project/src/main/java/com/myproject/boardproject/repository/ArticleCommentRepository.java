package com.myproject.boardproject.repository;

import com.myproject.boardproject.domain.ArticleComment;
import com.myproject.boardproject.domain.QArticleComment;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ArticleCommentRepository
        extends JpaRepository<ArticleComment,Long>
        , QuerydslPredicateExecutor<ArticleComment> // Entity의 기본 검색기능 추가해줌.
        , QuerydslBinderCustomizer<QArticleComment>  // QClass로 커스텀 검색기능 만들기 위함.
{
    @Override
    default void customize(QuerydslBindings bindings, QArticleComment root) {
        bindings.excludeUnlistedProperties(true); // 손택적으로 검색하기 위함.
        bindings.including( root.content, root.createdAt, root.createdBy); // 원하는 필드 추가
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase); // like '%${v}%'
        bindings.bind(root.createdAt).first(DateTimeExpression::eq);
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
    }
}
