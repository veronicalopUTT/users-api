package com.chakray.test.users.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User{

    private UUID id;

    private String email;

    private String name;

    private String phone;

    @JsonIgnore
    private String password;

    private String taxId;

    private String createdAt;

    private List<Address> addresses;

}