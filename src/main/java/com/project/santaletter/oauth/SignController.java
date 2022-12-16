package com.project.santaletter.oauth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class SignController {

    @GetMapping("/login")
    public String loginPage() {
        return "loginForm";
    }

}
