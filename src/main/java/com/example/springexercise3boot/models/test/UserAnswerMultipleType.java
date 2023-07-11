package com.example.springexercise3boot.models.test;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserAnswerMultipleType extends UserAnswer {

    private List<Integer> answer;

    public UserAnswerMultipleType(long questionId, List<Integer> answer) {
        super(questionId, QuestionType.MANY_ANSWERS);
        this.answer = answer;
    }
}
