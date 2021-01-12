package com.young.club.controller;

import com.young.club.security.dto.ClubAuthUserDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequestMapping("/sample/")
public class SampleController {

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

    @GetMapping("/admin")
    public void exAdmin() {
        log.info("exAdmin.....");
    }
}
