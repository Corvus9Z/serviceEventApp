package com.ServiceAggregatingEvents.project.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
@Data
@NoArgsConstructor
public class AdminDto {
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
}
