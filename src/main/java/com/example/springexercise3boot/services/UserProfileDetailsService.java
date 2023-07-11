package com.example.springexercise3boot.services;

import com.example.springexercise3boot.models.user.UserProfile;
import com.example.springexercise3boot.security.UserProfileDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserProfileDetailsService implements UserDetailsService {

    private final RestTemplate restTemplate;

    private final String apiGetUserByUsernameUrl;

    public UserProfileDetailsService(RestTemplate restTemplate, @Value("${property.apiHostUrl}") String apiHostUrl) {
        this.restTemplate = restTemplate;
        this.apiGetUserByUsernameUrl = apiHostUrl + "/api/user/{username}";
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserProfile userProfile = restTemplate.getForObject(apiGetUserByUsernameUrl, UserProfile.class, username);
        if (userProfile == null) {
            throw new UsernameNotFoundException("User not found!");
        }
        return new UserProfileDetails(userProfile);
    }

    public UserProfile getUserInfo() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return restTemplate.getForObject(apiGetUserByUsernameUrl, UserProfile.class, username);
    }
}
