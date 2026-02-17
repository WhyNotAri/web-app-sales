package com.ari.webapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    @Email(message = "email not valid")
    @NotBlank(message = "email is required")
    private String email;

    @NotBlank(message = "field cannot be empty")
    private String firstName;

    @NotBlank(message = "field cannot be empty")
    private String lastName;
}
