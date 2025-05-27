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
    @NotBlank
    private Date dateTime;
    @Min(0)
    private int quantity;
    @NotBlank
    private String status;
    @NotBlank
    private String purchaseUUID;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "customerId")
    private Customer customer;
    @NotBlank
    private String paymentProviderId;
}
