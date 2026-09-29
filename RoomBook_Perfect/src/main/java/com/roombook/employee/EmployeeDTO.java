package com.roombook.employee;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmployeeDTO(
        @NotBlank(message="Employee name is required") String name,
        @NotBlank(message="Employee email is required") @Email(message="Enter a valid email address") String email) {}
