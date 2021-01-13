package com.young.club.security.service;

import com.young.club.entity.ClubUser;
import com.young.club.entity.ClubUserRole;
import com.young.club.repository.ClubUserRepository;
import com.young.club.security.dto.ClubAuthUserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

// 로그인 처리가 끝난 결과를 가져오는 작업을 구성하기 위함
// OAuth2UserService 인터페이스는 UserDetailsService의 OAuth 버전
// 인터페이스를 직접 구현할 수도 있지만 좀더 편하게 클래스를 상속받아 구현함
@Log4j2
@Service
@RequiredArgsConstructor
public class ClubOAuth2UserDetailsService extends DefaultOAuth2UserService {

    private final ClubUserRepository repository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("-----------------------------");
        log.info("userRequest: " + userRequest);

        String clientName = userRequest.getClientRegistration().getClientName();

        log.info("clientName: " + clientName);
        log.info(userRequest.getAdditionalParameters());

        OAuth2User oAuth2User = super.loadUser(userRequest);

        log.info("=============================");
        oAuth2User.getAttributes().forEach((k, v) -> {
            log.info(k + ":" + v);
        });

        String email = null;

        if (clientName.equals("Google")) {
            email = oAuth2User.getAttribute("email");
        }

        log.info("EMAIL: " + email);
//        ClubUser user = saveSocialUser(email);
//
//        return oAuth2User;
        ClubUser user = saveSocialUser(email);

        ClubAuthUserDTO clubAuthUser = new ClubAuthUserDTO(
                user.getEmail(),
                user.getPassword(),
                true,
                user.getRoleSet().stream().map(
                        role -> new SimpleGrantedAuthority("ROLE_" + role.name())
                ).collect(Collectors.toList()),
                oAuth2User.getAttributes()
        );
        clubAuthUser.setName(user.getName());

        return clubAuthUser;
    }

    private ClubUser saveSocialUser(String email) {
        // 기존에 동일한 이메일로 가입한 유저 있으면 조회만
        Optional<ClubUser> result = repository.findByEmail(email, true);

        if (result.isPresent()) {
            return result.get();
        }
        // 없다면 회원 추가
        ClubUser clubUser = ClubUser.builder().email(email)
                .name(email)
                .password(passwordEncoder.encode("1111"))
                .fromSocial(true)
                .build();

        clubUser.addUserRole(ClubUserRole.USER);
        repository.save(clubUser);

        return clubUser;
    }
}
