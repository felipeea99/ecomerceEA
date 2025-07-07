package com.ecommerce.prorider.entities.store;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @ManyToOne
    @JoinColumn(name = "customerId")
    private Customer customer;

}

