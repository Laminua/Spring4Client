package com.example.springexercise3boot.models.test;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAnswerInputType extends UserAnswer {

    private String answer;

    public UserAnswerInputType(long questionId, String answer) {
        super(questionId, QuestionType.USER_INPUT);
        this.answer = answer;
    }
}
