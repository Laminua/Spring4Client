package com.example.springexercise3boot.services;

import com.example.springexercise3boot.dto.UserAnswerDTO;
import com.example.springexercise3boot.models.test.AssignedTest;
import com.example.springexercise3boot.models.test.Question;
import com.example.springexercise3boot.models.test.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class TestService {

    private final RestTemplate restTemplate;

    private final String apiGetAllTestsUrl;

    private final String apiGetTestsForUserUrl;

    private final String apiGetTestUrl;

    private final String apiGetQuestionsUrl;

    private final String apiPostUserResponseOnTest;

    @Autowired
    public TestService(RestTemplate restTemplate, @Value("${property.apiHostUrl}") String apiHostUrl) {

        this.restTemplate = restTemplate;
        this.apiGetAllTestsUrl = apiHostUrl + "/api/tests";
        this.apiGetTestsForUserUrl = apiHostUrl + "/api/tests/forUser/{id}";
        this.apiGetTestUrl = apiHostUrl + "/api/tests/{id}";
        this.apiGetQuestionsUrl = apiHostUrl + "/api/tests/questions/{id}";
        this.apiPostUserResponseOnTest = apiHostUrl + "/api/tests/questions/{id}";
    }

    public ResponseEntity<List<Test>> getAllTests() {

        Test[] testsArr = restTemplate.getForObject(apiGetAllTestsUrl, Test[].class);

        if (testsArr == null) {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
        }
        return new ResponseEntity<>(Arrays.asList(testsArr), HttpStatus.OK);
    }

    public ResponseEntity<List<AssignedTest>> getAssignedTestsForUser(long id) {

        AssignedTest[] tests = restTemplate.getForObject(apiGetTestsForUserUrl, AssignedTest[].class, id);

        if (tests == null) {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
        }
        return new ResponseEntity<>(Arrays.asList(tests), HttpStatus.OK);
    }

    public ResponseEntity getTest(long id) {

        try {
            Test test = restTemplate.getForObject(apiGetTestUrl, Test.class, id);
            return new ResponseEntity<>(test, HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getResponseBodyAsString(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<List<Question>> getQuestions(long id) {

        try {
            Question[] questions = restTemplate.getForObject(apiGetQuestionsUrl, Question[].class, id);
            return new ResponseEntity<>(Arrays.asList(questions), HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity(e.getResponseBodyAsString(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> postUserResponseOnTest(long id, UserAnswerDTO userAnswerDTO) {

        HttpEntity<UserAnswerDTO> request = new HttpEntity<>(userAnswerDTO);

        try {
            return restTemplate.exchange(apiPostUserResponseOnTest, HttpMethod.POST, request, String.class);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getResponseBodyAsString(), HttpStatus.BAD_REQUEST);
        }
    }
}
