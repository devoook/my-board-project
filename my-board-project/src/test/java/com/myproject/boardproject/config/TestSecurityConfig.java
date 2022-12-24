package com.myproject.boardproject.config;

import com.myproject.boardproject.domain.UserAccount;
import com.myproject.boardproject.repository.UserAccountRepository;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@Import(SecurityConfig.class)
public class TestSecurityConfig {

    @MockBean private UserAccountRepository userAccountRepository;

    @BeforeTestMethod // 각 테스트 메서드 실행되기 전에 실행됨
    public void securitySetUp() {
        given(userAccountRepository.findById(anyString())).willReturn(Optional.of(UserAccount.of(
                "devTest",
                "pw",
                "dev-test",
                "dev-test",
                "dev memo")
        ));
    }



}
