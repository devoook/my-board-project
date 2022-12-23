package com.myproject.boardproject.config;

import com.myproject.boardproject.dto.security.BoardPrincipal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@EnableJpaAuditing
@Configuration
public class JpaConfig {

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.ofNullable(SecurityContextHolder.getContext()) // 시큐리티에 대한 정보를 지닌 SecurityContextHolder 에서 SecurityContext 호출
                .map(SecurityContext::getAuthentication) // Authentication 정보를 가져와 인증 여부 확인할 것
                .filter(Authentication::isAuthenticated) // isAuthenticated (인증 되었는지 확인)
                .map(Authentication::getPrincipal)
                //  .map(x -> (BoardPrincipal) x)// 타입 캐스팅, 아래의 방법으로도 사용 가능
                .map(BoardPrincipal.class::cast)
                .map(BoardPrincipal::getUsername);
    }


}
