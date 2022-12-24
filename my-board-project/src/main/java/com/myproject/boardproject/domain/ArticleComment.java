package com.myproject.boardproject.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Getter
@ToString(callSuper = true)
@Table(indexes = {
        @Index(columnList = "content"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
public class ArticleComment extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(optional = false)  // optional = false 설정은 non-null 의미
    private Article article;

    @Setter
    @ManyToOne(optional = false) @JoinColumn(name = "userId")
    private UserAccount userAccount;

    @Setter @Column(nullable = false, length = 500)
    private String content;

    protected ArticleComment() {}

    private ArticleComment(Article article, UserAccount userAccount, String content) {
        this.userAccount = userAccount;
        this.article = article;
        this.content = content;
    }

    public static ArticleComment of (Article article, UserAccount userAccount, String hashtag) {
        return new ArticleComment(article, userAccount, hashtag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArticleComment that)) return false;
        return  id!=null && id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
