package com.ecommerce.ea.DTOs.response.auth;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginDTO {
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String userName;
    @NotBlank(message = "Password is required")
    private String password;
}
