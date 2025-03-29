package com.ServiceAggregatingEvents.project.controller;

import com.ServiceAggregatingEvents.project.dto.ApiResponseDto;
import com.ServiceAggregatingEvents.project.dto.UserDto;
import com.ServiceAggregatingEvents.project.entities.User;
import com.ServiceAggregatingEvents.project.repository.UserRepo;
import com.ServiceAggregatingEvents.project.service.UserMapper;
import com.ServiceAggregatingEvents.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final UserMapper userMapper; // Injectăm mapper-ul pentru a converti User în UserDto
    private final UserRepo userRepo;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDto<UserDto>> registerUser(@RequestBody UserDto userDto) {
        UserDto createdUser = userService.createUser(userDto);
        return new ResponseEntity<>(new ApiResponseDto<>("success", "User registered successfully", createdUser), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto<UserDto>> login(@RequestBody UserDto userDto) {
        System.out.println("Attempting to log in with email: " + userDto.getEmail());

        boolean isAuthenticated = userService.authenticate(userDto.getEmail(), userDto.getPassword());

        if (isAuthenticated) {
            System.out.println("Log in successful for user: " + userDto.getEmail());

            // Ne asigurăm că UserDto conține datele utilizatorului
            Optional<User> userOpt = userRepo.findByEmail(userDto.getEmail());
            if (userOpt.isPresent()) {
                UserDto loggedInUserDto = userMapper.convertToDto(userOpt.get()); // Conversie simplă a datelor utilizatorului autenticat
                return ResponseEntity.ok(new ApiResponseDto<>("success", "User logged in successfully", loggedInUserDto));
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponseDto<>("failure", "User data could not be retrieved", null));
        } else {
            System.out.println("Log in failed for user: " + userDto.getEmail());
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ApiResponseDto<>("failure", "Invalid credentials", null));
        }
    }

}
