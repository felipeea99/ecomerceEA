package com.ecommerce.ea.DTOs.update;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerUpdate {
    @NotBlank(message = "customerId is mandatory")
    private String customerId;

    @NotBlank(message = "userId is mandatory")
    private String userId;
}