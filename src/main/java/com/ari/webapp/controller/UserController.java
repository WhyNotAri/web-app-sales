package com.ari.webapp.controller;

import com.ari.webapp.dto.UserDto;
import com.ari.webapp.dto.UserLoginDto;
import com.ari.webapp.dto.UserRegisterDto;
import com.ari.webapp.dto.UserUpdateDto;
import com.ari.webapp.model.User;
import com.ari.webapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public User findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody UserRegisterDto userRegisterDto) {
        UserDto user = userService.register(userRegisterDto);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@Valid @RequestBody UserLoginDto userLoginDto) {
        UserDto user = userService.login(userLoginDto.getEmail(), userLoginDto.getPassword());
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable Long id, @Valid @RequestBody UserUpdateDto userUpdateDto) {
        UserDto updatedUser = userService.update(id, userUpdateDto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDto> delete(@Valid @PathVariable Long id) {
        UserDto deletedUser = userService.delete(id);
        return ResponseEntity.ok(deletedUser);
    }

}
