package com.example.springexercise3boot.models.test;

import com.example.springexercise3boot.models.user.UserProfile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AssignedTests {

    private long id;

//    private UserProfile userProfile;

    private Test test;

    private boolean finished;

    private int attempts;
}
