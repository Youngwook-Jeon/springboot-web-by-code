package com.young.club.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

// 인가/인증 작업에 사용
@Log4j2
@Getter
@Setter
@ToString
public class ClubAuthUserDTO extends User {

    private String email;

    private String name;

    private boolean fromSocial;

    public ClubAuthUserDTO(String username,
                           String password,
                           boolean fromSocial,
                           Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.email = username;
        this.fromSocial = fromSocial;
    }

}
