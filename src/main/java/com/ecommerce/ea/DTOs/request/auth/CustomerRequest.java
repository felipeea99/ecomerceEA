package com.ecommerce.ea.DTOs.request.auth;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {
    @NotBlank(message = "userId is mandatory")
    private String userId;
}
