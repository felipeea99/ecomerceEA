package com.ecommerce.prorider.DTOs.response.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserResponse {
    private UUID userId;
    private String name;
    private String userName;
    private String userLastName1;
    private String userLastName2;
    private String email;
    private String phoneNumber;
}
