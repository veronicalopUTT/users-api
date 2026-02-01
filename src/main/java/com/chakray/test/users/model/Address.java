package com.chakray.test.users.model;

import lombok.*;

@Data
@AllArgsConstructor
@Builder

public class Address{
    private Long id;
    private String name;
    private String street;
    private String countryCode;
}