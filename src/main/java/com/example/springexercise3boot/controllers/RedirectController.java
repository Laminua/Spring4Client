package com.example.springexercise3boot.controllers;

import com.example.springexercise3boot.models.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@Slf4j
public class RedirectController {

    @RequestMapping("/")
    public RedirectView redirectFromRootBasedOnRoleIfAlreadyLogged() {
        log.info("Redirecting logged user from root");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().toString();

        String redirectUrl = "";
        if (role.contains(Role.ROLE_ADMIN.toString())) {
            redirectUrl = "admin/index";
        } else if (role.contains(Role.ROLE_USER.toString())) {
            redirectUrl = "welcome";
        }
        return new RedirectView(redirectUrl);
    }
}
