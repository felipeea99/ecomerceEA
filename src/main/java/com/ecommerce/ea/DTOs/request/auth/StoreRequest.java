package com.ecommerce.ea.DTOs.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class StoreRequest {
    @NotBlank(message = "store name is mandatory")
    private String storeName;

    @NotBlank(message = "store email is mandatory")
    @Email(message = "invalid email format")
    private String storeEmail;

    @NotNull(message = "domain is mandatory")
    private String domain;

    @NotNull(message = "user is mandatory")
    private UUID userId;

}
