package com.ecommerce.ea.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ShoppingHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int historyId;
    @NotNull
    private Date dateTime;
    @Min(0)
    private int quantity;
    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusType status;
    @NotNull
    private String purchaseUUID;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "customerId")
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "addressId")
    private Address address;
    @ManyToOne
    @JoinColumn(name = "storeId")
    private Store store;
}
