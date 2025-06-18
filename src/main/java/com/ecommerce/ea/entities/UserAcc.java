package com.ecommerce.ea.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserAcc {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;
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
