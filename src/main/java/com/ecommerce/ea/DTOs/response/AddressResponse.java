package com.ecommerce.ea.DTOs.response;

import com.ecommerce.ea.entities.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponse {
    private int addressId;
    private String country;
    private String street;
    private String number;
    private String colony;
    private String town;

    public static AddressResponse toAddressResponseObj(Address address) {
        return new AddressResponse(
                address.getAddressId(),
                address.getCountry(),
                address.getStreet(),
                address.getNumber(),
                address.getColony(),
                address.getTown()
        );
    }
}
