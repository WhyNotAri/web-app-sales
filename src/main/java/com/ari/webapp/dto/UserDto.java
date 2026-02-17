package com.ari.webapp.dto;

import com.ari.webapp.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    @NotNull
    private Long id;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private Role role;

    public UserDto(String email, String firstName, String lastName, Role role) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;

    }
}
