package com.young.club.security.service;

import com.young.club.entity.ClubUser;
import com.young.club.repository.ClubUserRepository;
import com.young.club.security.dto.ClubAuthUserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

// 인증을 담당하는 AuthenticationManager가 내부적으로 UserDetailsService 호출하여 사용자의 정보를 가져옴
// 우리는 JPA를 사용하기에 이 부분을 UserDetailsService가 이용하는 구조로 만들자
@Log4j2
@Service
@RequiredArgsConstructor
public class ClubUserDetailsService implements UserDetailsService {

    private final ClubUserRepository clubUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("ClubUserDetailsService loadUserByUsername " + username);

        Optional<ClubUser> result = clubUserRepository.findByEmail(username, false);

        if (!result.isPresent()) {
            throw new UsernameNotFoundException("Check Email or Social");
        }

        ClubUser clubUser = result.get();

        log.info("------------------------");
        log.info(clubUser);

        ClubAuthUserDTO clubAuthUser = new ClubAuthUserDTO(
                clubUser.getEmail(),
                clubUser.getPassword(),
                clubUser.isFromSocial(),
                clubUser.getRoleSet().stream().map(role ->
                        new SimpleGrantedAuthority("ROLE_" + role.name())).collect(Collectors.toSet())
        );

        clubAuthUser.setName(clubUser.getName());
        clubAuthUser.setFromSocial(clubUser.isFromSocial());

        return clubAuthUser;
    }
}
