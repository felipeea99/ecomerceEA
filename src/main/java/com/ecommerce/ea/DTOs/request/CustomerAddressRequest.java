package com.ecommerce.ea.DTOs.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerAddressRequest {

    @NotNull(message = "customerId is mandatory")
    private String customerId;

    @NotNull(message = "addressId is mandatory")
    private Integer addressId;

}
