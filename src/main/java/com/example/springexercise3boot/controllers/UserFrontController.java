package com.example.springexercise3boot.controllers;

import com.example.springexercise3boot.dto.UserAnswerDTO;
import com.example.springexercise3boot.models.test.AssignedTest;
import com.example.springexercise3boot.models.test.Question;
import com.example.springexercise3boot.models.test.Test;
import com.example.springexercise3boot.models.user.UserProfile;
import com.example.springexercise3boot.services.TestService;
import com.example.springexercise3boot.services.UserProfileDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/")
@Slf4j
public class UserFrontController {

    private final UserProfileDetailsService userProfileDetailsService;

    private final TestService testService;

    @GetMapping("profile")
    public ModelAndView showWelcomePage() {
        log.debug("Front backend: profile information page has been requested");

        return new ModelAndView("profile");
    }

    @GetMapping("getUserInfo")
    public UserProfile getUserInfo() {
        log.debug("Front backend: Authenticated user profile details requested");

        return userProfileDetailsService.getUserInfo();
    }

    @GetMapping("testing")
    public ModelAndView showTestingPage() {
        log.debug("Front backend: Testing system page requested");

        return new ModelAndView("testing");
    }

    @GetMapping("tests")
    public ResponseEntity<List<Test>> getAllTests() {
        log.debug("Front backend: Requesting list of tests");

        return testService.getAllTests();
    }

    @GetMapping("tests/forUser/{id}")
    public ResponseEntity<List<AssignedTest>> getAssignedTestsForUser(@PathVariable long id) {
        log.debug("Front backend: Requesting tests for user with id {}", id);

        return testService.getAssignedTestsForUser(id);
    }

    @GetMapping("tests/{id}")
    public ResponseEntity getTest(@PathVariable long id) {
        log.debug("Front backend: Requesting test with id {}", id);

        return testService.getTest(id);
    }

    @GetMapping("runTest")
    public ModelAndView runTest() {
        log.debug("Front backend: running test");

        return new ModelAndView("questions");
    }

    @GetMapping("tests/questions/{userId}/{testId}")
    public ResponseEntity<List<Question>> getQuestions(@PathVariable long userId, @PathVariable long testId) {
        log.debug("Front backend: requesting questions for test with id {} for user with id {}", testId, userId);

        return testService.getQuestions(userId, testId);
    }

    @PostMapping("tests/questions/{id}")
    public ResponseEntity<String> postUserResponseOnTest(@PathVariable long id, UserAnswerDTO userAnswerDTO) {
        log.debug("Front backend: sending user response on test with id {} to api", id);

        return testService.postUserResponseOnTest(id, userAnswerDTO);
    }
}
