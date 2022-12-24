package com.myproject.boardproject.dto.security;

import com.myproject.boardproject.dto.UserAccountDto;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public record BoardPrincipal(
        String username,
        String password,
        Collection<? extends GrantedAuthority> authorities,
        String email,
        String nickname,
        String memo
) implements UserDetails {
    // 현 프로젝트에서는 권한을 사용하지 않을 것임. 따라서 권한을 따로 주지 않고 , 기본 값을 준다.
    public static BoardPrincipal of(String username, String password, String email, String nickname, String memo) {
        Set<RoleType> roleTypes = Set.of(RoleType.USER);

        return new BoardPrincipal(
                username,
                password,
                //    Collection<? extends GrantedAuthority> authorities 으로 변환하기 위한 코드
                roleTypes.stream()
                        .map(RoleType::getName)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toUnmodifiableSet())
                ,
                email,
                nickname,
                memo
        );
    }

    public static BoardPrincipal from(UserAccountDto dto) {
        return BoardPrincipal.of(
                dto.userId(),
                dto.userPassword(),
                dto.email(),
                dto.nickname(),
                dto.memo()
        );
    }

    public UserAccountDto toDto() {
        return UserAccountDto.of(
                username,
                password,
                email,
                nickname,
                memo
        );
    }




    // 인증 - 로그인 했느냐 안했느냐, 권한 - 로그인 한 회원이 어떤 회원인가
    @Override public Collection<? extends GrantedAuthority> getAuthorities() {return authorities;}

    @Override public String getUsername() {return username;}
    @Override public String getPassword() {return password;}

    // 아래 설정은 유저의 디테일한 설정 하는 것, 지금은 true로 설정해주자. -> UserDetails (인터페이스) 대신 User 구현체를 사용하면 된다.
    @Override public boolean isAccountNonExpired() {return true;}
    @Override public boolean isAccountNonLocked() {return true;}
    @Override public boolean isCredentialsNonExpired() {return true;}
    @Override public boolean isEnabled() {return true;}

    // 확장성을 위해서 enum 타입으로 권한을 만듦.
    public enum RoleType{
        USER("ROLE_USER"); // ROLE_ prefix는 SpringSecurity의 권한 규칙

        @Getter
        private final String name;

        RoleType(String name) {
            this.name = name;
        }
    }

}
