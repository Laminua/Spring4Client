package com.example.springexercise3boot.services;

import com.example.springexercise3boot.dto.UserProfileDTO;
import com.example.springexercise3boot.models.user.UserProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class AdminService {

    private final RestTemplate restTemplate;

    private final String apiGetUsersListUrl;

    private final String apiGetUserByIdUrl;

    private final String apiUpdateUserUrl;

    private final String apiDeleteUserUrl;

    public AdminService(RestTemplate restTemplate, @Value("${property.apiHostUrl}") String apiHostUrl) {
        this.restTemplate = restTemplate;
        this.apiGetUsersListUrl = apiHostUrl + "/api/users";
        this.apiGetUserByIdUrl = apiHostUrl + "/api/users/{id}";
        this.apiUpdateUserUrl = apiHostUrl + "/api/updateUser";
        this.apiDeleteUserUrl = apiHostUrl + "/api/deleteUser?id=";
    }

    public ResponseEntity<List<UserProfileDTO>> getUsers() {

        UserProfileDTO[] userProfilesArr = restTemplate.getForObject(apiGetUsersListUrl, UserProfileDTO[].class);

        if (userProfilesArr == null) {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
        }
        return new ResponseEntity<>(Arrays.asList(userProfilesArr), HttpStatus.OK);
    }

    public ResponseEntity<String> addUser(UserProfileDTO profileDTO, BindingResult bindingResult)
            throws BindException {

        if (bindingResult.hasErrors()) {
            log.error("User can't be added because of invalid data");
            throw new BindException(bindingResult);
        }

        HttpEntity<UserProfileDTO> request = new HttpEntity<>(profileDTO);

        try {
            return restTemplate.exchange(apiGetUsersListUrl, HttpMethod.POST, request, String.class);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getResponseBodyAsString(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> updateUser(UserProfileDTO profileDTO, BindingResult bindingResult)
            throws BindException {

        if (bindingResult.hasErrors()) {
            log.error("User can't be updated because of invalid data");
            throw new BindException(bindingResult);
        }

        HttpEntity<UserProfileDTO> request = new HttpEntity<>(profileDTO);

        try {
            return restTemplate.exchange(apiUpdateUserUrl, HttpMethod.POST, request, String.class);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getResponseBodyAsString(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity getUserProfileById(long id) {

        try {
            UserProfile profile = restTemplate.getForObject(apiGetUserByIdUrl, UserProfile.class, id);
            return new ResponseEntity<>(profile, HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getResponseBodyAsString(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> deleteUser(long id) {

        try {
            restTemplate.delete(apiDeleteUserUrl + id);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getResponseBodyAsString(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("User successfully deleted", HttpStatus.OK);
    }
}
