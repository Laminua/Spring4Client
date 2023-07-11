package com.example.springexercise3boot.models.test;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "questionType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = UserAnswerInputType.class, name = QuestionType.USER_INPUT_CONST),
        @JsonSubTypes.Type(value = UserAnswerMultipleType.class, name = QuestionType.MANY_ANSWERS_CONST),
        @JsonSubTypes.Type(value = UserAnswerSingleType.class, name = QuestionType.SINGLE_ANSWER_CONST)
})
public abstract class UserAnswer {

    private long questionId;

    private QuestionType questionType;
}
