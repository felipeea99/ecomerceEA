package com.ecommerce.prorider.entities.store;

import com.ecommerce.prorider.entities.auth.Customer;
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

    @NotNull
    private boolean isCompleted;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    @NotNull
    /// Might be true or false
    private boolean isSize;

    /// Might be Null
    private Size sizeObj;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "customerId")
    private Customer customer;
}
