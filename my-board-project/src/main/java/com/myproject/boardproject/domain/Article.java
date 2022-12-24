package com.myproject.boardproject.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString(callSuper = true)
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Article extends AuditingFields{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter @ManyToOne(optional = false) @JoinColumn(name = "userId")
    private UserAccount userAccount;

    @Setter
    @Column(nullable = false) //@Column 기본값 true
    private String title; // 제목
    @Setter
    @Column(nullable = false, length = 10000)
    private String content; // 내용
    @Setter
    private String hashtag; // 해시태그
    @ToString.Exclude // 순환참조 방지
    @OrderBy("createdAt DESC") // 정렬 기준
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    // 실무에서는 양방향 바인딩을 일부로 풀고 사용하는 경우가 많음, cascade의 속성이 필요하지 않는 경우 있음.. -> 게시글 삭제되도 댓글은 남겨야함.
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>(); // list, set , map을 다양하게 사용한다.

    // JPA는 기본생성자 필요 , private은 안됨
    protected Article() {
    }

    // 생성자를 private로 막고 팩토리 메서드를 통해 사용하는 방법
    private Article(UserAccount userAccount,String title, String content, String hashtag) {
        this.userAccount = userAccount;
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    // 팩토리 메스드를 사용하여 객체 생성
    public static Article of(UserAccount userAccount,String title, String content, String hashtag) {
        return new Article(userAccount,title, content, hashtag);
    }

    // 동일성 동등성 검사를 위한 ... lombok의 equalsAndHashCode 사용하면 전체 필드를 사용하게됨 ..

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article article)) return false;
        return id != null && id.equals(article.getId()); // id == null 이면, 영속화가 되지 않은 것. 다른 엔티티와 연관관계를 맺을때 필요함.
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
