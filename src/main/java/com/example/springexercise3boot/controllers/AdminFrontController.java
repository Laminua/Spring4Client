package com.example.springexercise3boot.controllers;

import com.example.springexercise3boot.dto.UserProfileDTO;
import com.example.springexercise3boot.models.UserProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/admin/")
public class AdminFrontController {
    private final String URI_USERS = "http://localhost:8081/api/users";
    private final String URI_USERPROFILE_ID = "http://localhost:8081/api/users/{id}";
    RestTemplate restTemplate;

    @Autowired
    public AdminFrontController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("index")
    public ModelAndView showAdminPage() {
        log.info("Front backend request: show Admin page");

        return new ModelAndView("admin/index");
    }

    @GetMapping(value = "users")
    public ResponseEntity<List<UserProfile>> getUsers() {
        log.info("Front backend request: requesting list of UserProfiles");

        UserProfile[] userProfilesArr = restTemplate.getForObject(URI_USERS, UserProfile[].class);

        if (userProfilesArr == null) {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
        }
        return new ResponseEntity<>(Arrays.asList(userProfilesArr), HttpStatus.OK);
    }

    @GetMapping("addUserForm")
    public ModelAndView showAddUserForm() {
        log.info("Front backend request: show \"Add user\" page");

        return new ModelAndView("admin/add-user");
    }

    @PostMapping("users")
    public ResponseEntity<String> addUser(@Valid UserProfileDTO profileDTO, BindingResult bindingResult)
            throws BindException {
        log.info("Front backend request: requesting endpoint to create UserProfile");

        if (bindingResult.hasErrors()) {
            log.error("User can't be added because of invalid data");
            throw new BindException(bindingResult);
        }

        HttpEntity<UserProfileDTO> request = new HttpEntity<>(profileDTO);

        try {
            return restTemplate.exchange(URI_USERS, HttpMethod.POST, request, String.class);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getResponseBodyAsString(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("updateUserForm")
    public ModelAndView showUpdateUserForm() {
        log.info("Front backend request: show \"Update user\" page");

        return new ModelAndView("admin/update-user");
    }

    @PostMapping("updateUser")
    public ResponseEntity<String> updateUser(@Valid UserProfileDTO profileDTO, BindingResult bindingResult)
            throws BindException {
        log.info("Front backend request: updating UserProfile in database. Name: "
                + profileDTO.getName()
                + " Email: "
                + profileDTO.getEmail());

        if (bindingResult.hasErrors()) {
            log.error("User can't be updated because of invalid data");
            throw new BindException(bindingResult);
        }

        HttpEntity<UserProfileDTO> request = new HttpEntity<>(profileDTO);
        String urlToUpdate = "http://localhost:8081/api/updateUser";
        try {
            return restTemplate.exchange(urlToUpdate, HttpMethod.POST, request, String.class);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getResponseBodyAsString(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("users/{id}")
    public ResponseEntity getUserProfileById(@PathVariable int id) {
        log.info("Front backend request: requesting UserProfile with id " + id);
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));
        try {
            UserProfile profile = restTemplate.getForObject(URI_USERPROFILE_ID, UserProfile.class, params);
            return new ResponseEntity<>(profile, HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getResponseBodyAsString(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("deleteUser")
    public ResponseEntity<String> deleteUser(@RequestParam("id") int id) {
        log.info("Front backend request: requesting delete UserProfile with id " + id);

        try {
            restTemplate.delete("http://localhost:8081/api/deleteUser?id=" + id);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getResponseBodyAsString(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("User successfully deleted", HttpStatus.OK);
    }
}
