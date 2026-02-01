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
    private String phone;

    @NotBlank
    private String password;

    @NotBlank
    private String taxId;

    @NotNull
    @Size(min = 1)
    private List<AddressRequest> addresses;
}
