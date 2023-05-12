package com.example.springexercise3boot.dto;

import com.example.springexercise3boot.models.Role;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserProfileDTO {
    private int id;
    @NotEmpty(message = "Username should not be empty")
    @Size(min = 4, max = 30, message = "Username should be between 3 and 30 characters")
    private String username;
    @NotEmpty(message = "Password should not be empty")
    @Size(min = 4, max = 12, message = "Password should be between 4 and 12 characters")
    private String password;
    @NotNull(message = "Role should not be empty")
    private Role role;
    @NotEmpty(message = "Name should not be empty")
    private String name;
    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    private String email;
}
