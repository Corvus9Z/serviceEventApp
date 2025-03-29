package com.ServiceAggregatingEvents.project.dto;

import com.ServiceAggregatingEvents.project.entities.Role;
import com.ServiceAggregatingEvents.project.entities.RoleType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserDto {
    private long id;
    @NotEmpty
    @NotBlank(message = "Name is mandatory")
    private String name;
    private String lastName;

    @NotEmpty
    @NotBlank(message = "Email is mandatory")
    @Email
    private String email;
    @NotEmpty
    @NotBlank(message = "Password is mandatory.")
    private String password;
    @NotEmpty(message = "Roles is mandatory.")
    private List<Role> roles = new ArrayList<>();

}
