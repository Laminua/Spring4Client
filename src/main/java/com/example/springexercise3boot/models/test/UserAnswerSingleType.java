package com.example.springexercise3boot.models.test;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAnswerSingleType extends UserAnswer {

    private int answer;

    public UserAnswerSingleType(long questionId, int answer) {
        super(questionId, QuestionType.SINGLE_ANSWER);
        this.answer = answer;
    }
}
