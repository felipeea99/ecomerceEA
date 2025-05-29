package com.ecommerce.ea.DTOs.request;

import com.ecommerce.ea.entities.CustomerAddress;
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

    public CustomerAddress ToCustomerAddressObj(){
        CustomerAddress customerAddress = new CustomerAddress();
        this.setCustomerId(customerId);
        this.setAddressId(addressId);
        return customerAddress;
    }
}
