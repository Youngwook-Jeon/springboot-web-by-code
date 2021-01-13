package com.young.club.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

// 인가/인증 작업에 사용
// 소셜 로그인 사용시 컨트롤러가 ClubAuthUserDTO 타입을 사용함, 이때 OAuth2User 타입도 사용할 수 있게 수정
@Log4j2
@Getter
@Setter
@ToString
public class ClubAuthUserDTO extends User implements OAuth2User {

    private String email;

    private String password;

    private String name;

    private boolean fromSocial;

    private Map<String, Object> attr;

    public ClubAuthUserDTO(String username,
                           String password,
                           boolean fromSocial,
                           Collection<? extends GrantedAuthority> authorities,
                           Map<String, Object> attr) {
        this(username, password, fromSocial, authorities);
        this.attr = attr;
    }

    public ClubAuthUserDTO(String username,
                           String password,
                           boolean fromSocial,
                           Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.email = username;
        this.password = password;
        this.fromSocial = fromSocial;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attr;
    }
}
