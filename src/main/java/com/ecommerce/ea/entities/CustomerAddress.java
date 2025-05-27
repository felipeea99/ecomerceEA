package com.ecommerce.ea.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CustomerAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerAddressId;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "customerId")
    private Customer customer;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "addressId")
    private Address address;
}
