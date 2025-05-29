package com.ecommerce.ea.DTOs.request;

import com.ecommerce.ea.entities.Cart;
import com.ecommerce.ea.entities.Customer;
import com.ecommerce.ea.entities.Product;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class CartRequest {
    @Min(1)
    private int quantity;
    private boolean isCompleted;
    @NotNull
    private Product product;
    @NotNull
    private Customer customer;

    public Cart ToCartObj() {
        Cart cart = new Cart();
        cart.setQuantity(this.quantity);
        cart.setCompleted(this.isCompleted);
        cart.setProduct(this.product);
        cart.setCustomer(this.customer);
        return cart;
    }
}
