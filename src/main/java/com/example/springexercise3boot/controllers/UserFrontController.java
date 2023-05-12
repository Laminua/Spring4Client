package com.example.springexercise3boot.controllers;

import com.example.springexercise3boot.models.UserProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/user/")
@Slf4j
public class UserFrontController {

    RestTemplate restTemplate;

    @Autowired
    public UserFrontController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("welcome")
    public ModelAndView showWelcomePage() {
        log.info("Front backend request: welcome page for access level \"USER\" has been requested");

        return new ModelAndView("welcome");
    }

    @GetMapping("getUserInfo")
    public UserProfile getUserInfo() {
        log.info("Front backend request: Authenticated user profile details requested");

        String url = "http://localhost:8081/api/user/{username}";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return restTemplate.getForObject(url, UserProfile.class, username);
    }
}
