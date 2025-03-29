package com.ServiceAggregatingEvents.project.controller;

import com.ServiceAggregatingEvents.project.dto.ApiResponseDto;
import com.ServiceAggregatingEvents.project.dto.UserDto;
import com.ServiceAggregatingEvents.project.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/my-account")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // ✅ Update user (folosit în pagina My Account)
    @PutMapping("/update/{userId}")
    public ResponseEntity<ApiResponseDto<UserDto>> updateUser(
            @PathVariable Long userId,
            @Valid @RequestBody UserDto updateUserDto) {

        UserDto updatedUser = userService.editUser(userId, updateUserDto);
        return new ResponseEntity<>(new ApiResponseDto<>(
                "success", "User updated successfully", updatedUser), HttpStatus.OK);
    }

    // ✅ Obține toți utilizatorii (Admin Panel - tab Users)
    @GetMapping("/all")
    public ResponseEntity<ApiResponseDto<List<UserDto>>> getAllUsers() {
        List<UserDto> users = userService.findAllUsers();
        return new ResponseEntity<>(new ApiResponseDto<>(
                "success", "Users retrieved successfully", users), HttpStatus.OK);
    }

    // ✅ Șterge un utilizator după ID (Admin Panel - delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>(new ApiResponseDto<>(
                "success", "User deleted successfully", null), HttpStatus.OK);
    }
}
