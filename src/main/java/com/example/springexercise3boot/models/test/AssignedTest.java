package com.example.springexercise3boot.models.test;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AssignedTest {

    private long id;

//    private UserProfile userProfile;

    private Test test;

    private boolean finished;

    private int attempts;
}
