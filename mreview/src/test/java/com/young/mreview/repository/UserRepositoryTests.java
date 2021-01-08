package com.young.mreview.repository;

import com.young.mreview.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void insertUsers() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            User user = User.builder()
                    .email("r" + i + "@naver.com")
                    .pw("1111")
                    .nickname("reviewer" + i).build();
            userRepository.save(user);
        });
    }
}
