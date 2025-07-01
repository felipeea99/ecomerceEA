package com.ecommerce.ea.DTOs.request.auth;

import com.ecommerce.ea.entities.auth.RoleType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAccRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String userName;
    @NotBlank
    private String userLastName1;
    @NotBlank
    private String userLastName2;
    @NotBlank
    private String password;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String phoneNumber;
    @NotNull
    @Enumerated(EnumType.STRING)
    private RoleType role;
}
