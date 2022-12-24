package com.myproject.boardproject.repository;

import com.myproject.boardproject.domain.Article;
import com.myproject.boardproject.domain.UserAccount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;



//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // DataJpaTest 의 테스트 DB 사용하지 않고 yml testdb 설정 사용 위해...
@DisplayName("JPA 연결 테스트 ")
@Import(JpaRepositoryTest.TestJpaConfig.class) // 스프링 시큐리티 적용후 테스트를 위함.
@DataJpaTest // 내부의 SpringExtension 덕에  생성자 주입 가능해짐 & transactional
class JpaRepositoryTest {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;
    private final UserAccountRepository userAccountRepository;

    @Autowired
    public JpaRepositoryTest(ArticleRepository articleRepository, ArticleCommentRepository articleCommentRepository,
    UserAccountRepository userAccountRepository) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
        this.userAccountRepository = userAccountRepository;
    }

    @DisplayName("select 테스트 ")
    @Test
    void givenTestData_whenSelecting_thenWorksFine() {
        //given

        //when
        List<Article> articles = articleRepository.findAll();

        //then
        assertThat(articles)
                .isNotNull()
                .hasSize(123);

    }

    @DisplayName("insert 테스트 ")
    @Test
    void givenTestData_whenInserting_thenWorksFine() {
        //given
        long previousCount = articleRepository.count();
        UserAccount userAccount = userAccountRepository.save(UserAccount.of("dev", "pw", null, null, null));
        Article article = Article.of(userAccount, "new article", "new content", "#spring");
        //when
        articleRepository.save(article);

        //then
        assertThat(articleRepository.count()).isEqualTo(previousCount + 1);
    }

    @DisplayName("update 테스트 ")
    @Test
    void givenTestData_whenUpdating_thenWorksFine() {
        //given
        Article article = articleRepository.findById(1L).orElseThrow();
        String updatedHashtag = "#springboot";
        article.setHashtag(updatedHashtag);
        long previousCount = articleRepository.count();

        //when
        Article savedArticle = articleRepository.saveAndFlush(article);

        //then
        assertThat(savedArticle).hasFieldOrPropertyWithValue("hashtag", updatedHashtag);
    }


    @DisplayName("delete 테스트 ")
    @Test
    void givenTestData_whenDeleting_thenWorksFine() {
        //given
        Article article = articleRepository.findById(1L).orElseThrow();
        long previousArticleCount = articleRepository.count();
        long previousArticleCommentCount = articleCommentRepository.count();
        int deletedCommentSize = article.getArticleComments().size();



        //when
        articleRepository.delete(article);

        //then
        assertThat(articleRepository.count()).isEqualTo(previousArticleCount - 1);
        assertThat(articleCommentRepository.count()).isEqualTo(previousArticleCommentCount - deletedCommentSize);
    }

    @EnableJpaAuditing
    @TestConfiguration // 테스트 시에만 빈등록
    public static class TestJpaConfig {
        @Bean
        public AuditorAware<String> auditorAware() {
            return () -> Optional.of("dev");
        }
    }


}
