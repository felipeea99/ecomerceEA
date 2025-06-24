package com.ecommerce.ea.DTOs.response.store;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

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
    private UUID customerId;

}
