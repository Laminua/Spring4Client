package com.example.springexercise3boot.dto;

import com.example.springexercise3boot.models.test.UserAnswer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAnswerDTO {

    private long userId;

    private List<UserAnswer> userAnswers;
}
