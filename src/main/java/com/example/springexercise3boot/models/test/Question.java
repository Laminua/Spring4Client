package com.example.springexercise3boot.models.test;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class Question {

    private long id;

    private String question;

    private QuestionType questionType;

    private Map<Integer, String> answers;
}
