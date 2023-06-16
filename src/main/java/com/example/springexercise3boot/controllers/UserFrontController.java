package com.example.springexercise3boot.controllers;

import com.example.springexercise3boot.models.test.AssignedTests;
import com.example.springexercise3boot.models.test.Question;
import com.example.springexercise3boot.models.test.Test;
import com.example.springexercise3boot.models.user.UserProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/user/")
@Slf4j
public class UserFrontController {

    RestTemplate restTemplate;

    @Autowired
    public UserFrontController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("profile")
    public ModelAndView showWelcomePage() {
        log.info("Front backend: profile information page has been requested");

        return new ModelAndView("profile");
    }

    @GetMapping("getUserInfo")
    public UserProfile getUserInfo() {
        log.info("Front backend: Authenticated user profile details requested");

        String apiGetUserByUsernameUrl = "http://localhost:8081/api/user/{username}";

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return restTemplate.getForObject(apiGetUserByUsernameUrl, UserProfile.class, username);
    }

    @GetMapping("testing")
    public ModelAndView showTestingPage() {
        log.info("Front backend: Testing system page requested");

        return new ModelAndView("testing");
    }

    @GetMapping("tests")
    public ResponseEntity<List<Test>> getAllTests() {
        log.info("Front backend: Requesting list of tests");

        String apiGetAllTestsUrl = "http://localhost:8081/api/tests";

        Test[] testsArr = restTemplate.getForObject(apiGetAllTestsUrl, Test[].class);

        if (testsArr == null) {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
        }
        return new ResponseEntity<>(Arrays.asList(testsArr), HttpStatus.OK);
    }

    @GetMapping("tests/forUser/{id}")
    ResponseEntity<List<AssignedTests>> getAssignedTestsForUser(@PathVariable long id) {
        log.info("Front backend: Requesting tests for user with id " + id);

        String apiGetTestsForUserUrl = "http://localhost:8081/api/tests/forUser/{id}";

        AssignedTests[] tests = restTemplate.getForObject(apiGetTestsForUserUrl, AssignedTests[].class, id);

        if (tests == null) {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
        }
        return new ResponseEntity<>(Arrays.asList(tests), HttpStatus.OK);
    }

    @GetMapping("tests/{id}")
    ResponseEntity getTest(@PathVariable long id) {
        log.info("Front backend: Requesting test with id " + id);

        String apiGetTestUrl = "http://localhost:8081/api/tests/{id}";

        try {
            Test test = restTemplate.getForObject(apiGetTestUrl, Test.class, id);
            return new ResponseEntity<>(test, HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getResponseBodyAsString(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("runTest")
    public ModelAndView runTest() {
        log.info("Front backend: running test");

        return new ModelAndView("questions");
    }

    @GetMapping("tests/questions/{id}")
    ResponseEntity<List<Question>> getQuestions(@PathVariable long id) {

        String apiGetQuestionsUrl = "http://localhost:8081/api/tests/questions/{id}";

        try {
            Question[] questions = restTemplate.getForObject(apiGetQuestionsUrl, Question[].class, id);
            return new ResponseEntity<>(Arrays.asList(questions), HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity(e.getResponseBodyAsString(), HttpStatus.BAD_REQUEST);
        }

    }
}
