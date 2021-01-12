package com.young.club.security;

import com.young.club.entity.ClubUser;
import com.young.club.entity.ClubUserRole;
import com.young.club.repository.ClubUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class ClubUserTests {

    @Autowired
    private ClubUserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void insertDummies() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            ClubUser clubUser = ClubUser.builder()
                    .email("user" + i + "@naver.com")
                    .name("사용자" + i)
                    .fromSocial(false)
                    .password(passwordEncoder.encode("1111"))
                    .build();

            clubUser.addUserRole(ClubUserRole.USER);
            if (i > 80) {
                clubUser.addUserRole(ClubUserRole.MANAGER);
            }
            if (i > 90) {
                clubUser.addUserRole(ClubUserRole.ADMIN);
            }

            repository.save(clubUser);
        });
    }

    @Test
    public void testRead() {
        Optional<ClubUser> result = repository.findByEmail("user95@naver.com", false);
        ClubUser clubUser = result.get();
        System.out.println(clubUser);
    }
}
