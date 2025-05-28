package com.ecommerce.ea.DTOs.update;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressCustomerUpdate {
    @NotNull(message = "customerAddressId is mandatory")
    private int customerAddressId;
    @NotNull(message = "customerId is mandatory")
    private String customerId;
    @NotNull(message = "addressId is mandatory")
    private int addressId;
}
