package com.ecommerce.ea.DTOs.response;

import com.ecommerce.ea.entities.CustomerAddress;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerAddressResponse {
    private int customerAddressId;
    private String customerId;
    private int addressId;

    public static CustomerAddressResponse ToCustomerAddressResponse(CustomerAddress customerAddress){
        return new CustomerAddressResponse(
                customerAddress.getCustomerAddressId(),
                customerAddress.getCustomer().getUser().getUserId(),
                customerAddress.getAddress().getAddressId()
        );
    }
}
