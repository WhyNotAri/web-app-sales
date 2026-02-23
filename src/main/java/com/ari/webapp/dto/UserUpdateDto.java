package com.ari.webapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserUpdateDto {
    private String email;
    private String password;
    private String confirmPassword;
    private String firstName;
    private String lastName;
}
