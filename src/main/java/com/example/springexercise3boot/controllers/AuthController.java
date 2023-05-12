package com.example.springexercise3boot.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    @GetMapping("/login")
    public ModelAndView loginPage() {
        log.info("Authentication page has been requested");
        return new ModelAndView("auth/login");
    }
}

