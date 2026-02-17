package com.ari.webapp.service;

import com.ari.webapp.dto.UserDto;
import com.ari.webapp.dto.UserRegisterDto;
import com.ari.webapp.dto.UserUpdateDto;
import com.ari.webapp.model.Role;
import com.ari.webapp.model.User;
import com.ari.webapp.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto register(UserRegisterDto dto) {
        if(userRepository.findByEmail(dto.getEmail()).isPresent()){
            throw new RuntimeException("Email Already Exists");
        }
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setRole(Role.USER);

        User savedUser = userRepository.save(user);
        return new UserDto(savedUser.getEmail(), savedUser.getFirstName(), savedUser.getLastName(), savedUser.getRole());
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public UserDto delete(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setRole(user.getRole());

        return userDto;
    }

    public UserDto update(Long id, UserUpdateDto userDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setEmail(user.getEmail());
        user.setPassword(user.getPassword());
        user.setFirstName(user.getFirstName());
        user.setLastName(user.getLastName());

        User updatedUser = userRepository.save(user);

        UserDto response =  new UserDto();
        response.setId(updatedUser.getId());
        response.setEmail(updatedUser.getEmail());
        response.setFirstName(updatedUser.getFirstName());
        response.setLastName(updatedUser.getLastName());
        response.setRole(updatedUser.getRole());

        return response;
    }
}
