package com.ecommerce.prorider.DTOs.request.store;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequest {
    @NotBlank(message = "country is mandatory")
    @Size(min = 4)
    private String country;
    @NotBlank(message = "street is mandatory")
    private String street;
    @NotBlank(message = "number is mandatory")
    private String number;
    @NotBlank(message = "colony is mandatory")
    private String colony;
    @NotBlank(message = "town is mandatory")
    private String town;
    @NotBlank(message = "customerId is mandatory")
    private UUID customerId;
}
