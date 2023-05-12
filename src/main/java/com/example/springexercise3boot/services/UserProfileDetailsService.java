package com.example.springexercise3boot.services;

import com.example.springexercise3boot.models.UserProfile;
import com.example.springexercise3boot.security.UserProfileDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserProfileDetailsService implements UserDetailsService {

    RestTemplate restTemplate;

    @Autowired
    public UserProfileDetailsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        String url = "http://localhost:8081/api/user/{username}";
        UserProfile userProfile = restTemplate.getForObject(url, UserProfile.class, username);
        if (userProfile == null) {
            throw new UsernameNotFoundException("User not found!");
        }
        return new UserProfileDetails(userProfile);
    }
}
