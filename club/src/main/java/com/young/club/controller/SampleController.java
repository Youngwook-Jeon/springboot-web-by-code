package com.young.club.controller;

import com.young.club.security.dto.ClubAuthUserDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequestMapping("/sample/")
public class SampleController {

    @PreAuthorize("permitAll()")
    @GetMapping("/all")
    public void exAll() {
        log.info("exAll.....");
    }

    @GetMapping("/user")
    public void exUser(@AuthenticationPrincipal ClubAuthUserDTO clubAuthUser) {
        log.info("exUser.....");
        log.info("-----------------------");

        log.info(clubAuthUser);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public void exAdmin() {
        log.info("exAdmin.....");
    }

    @PreAuthorize("#clubAuthUser != null && #clubAuthUser.username eq \"user95@naver.com\"")
    @GetMapping("/exOnly")
    public String exUserOnly(@AuthenticationPrincipal ClubAuthUserDTO clubAuthUser) {
        log.info("exUserOnly.....");
        log.info(clubAuthUser);

        return "/sample/admin";
    }
}
