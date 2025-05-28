package com.ecommerce.ea.DTOs.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreRequest {
    @NotBlank(message = "store name is mandatory")
    private String storeName;

    @NotBlank(message = "store email is mandatory")
    @Email(message = "invalid email format")
    private String storeEmail;

    @NotNull(message = "user is mandatory")
    private String userId;
}
