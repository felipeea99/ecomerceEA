package com.ecommerce.ea.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartId;
    @Min(1)
    private int quantity;
    private boolean isCompleted;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "customerId")
    private Customer customer;
}
