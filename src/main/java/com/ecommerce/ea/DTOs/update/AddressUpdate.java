package com.ecommerce.ea.DTOs.update;


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
public class AddressUpdate {
    @Min(value = 1, message = "addressId must be greater than 0")
    private int addressId;
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
}
