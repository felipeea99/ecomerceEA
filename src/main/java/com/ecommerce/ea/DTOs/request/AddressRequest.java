package com.ecommerce.ea.DTOs.request;

import com.ecommerce.ea.entities.Address;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    public Address ToAddressObj() {
        Address address = new Address();
        address.setCountry(this.country);
        address.setStreet(this.street);
        address.setNumber(this.number);
        address.setColony(this.colony);
        address.setTown(this.town);
        return address;
    }
}
