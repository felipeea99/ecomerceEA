package com.ecommerce.prorider.DTOs.update.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class StoreUpdate {
    private UUID storeID;

    @NotBlank(message = "store name is mandatory")
    private String storeName;

    @NotBlank(message = "store email is mandatory")
    @Email(message = "invalid email format")
    private String storeEmail;

    @NotBlank(message = "domain email is mandatory")
    private String domain;
    @NotBlank(message = "userId email is mandatory")
    private UUID userId;

}
