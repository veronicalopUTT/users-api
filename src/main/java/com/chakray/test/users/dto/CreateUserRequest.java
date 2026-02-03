package com.chakray.test.users.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class CreateUserRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    @Pattern(
            regexp = "^(\\+\\d{1,3}\\s?)?\\d{10}$",
            message = "Invalid phone format"
    )
    private String phone;


    @NotBlank
    private String password;

    @NotBlank
    @Pattern(
            regexp = "^[A-ZÑ&]{3}[0-9]{6}[A-Z0-9]{3}$",
            message = "Invalid RFC format"
    )
    private String taxId;


    @NotNull
    @Size(min = 1)
    private List<AddressRequest> addresses;
}
