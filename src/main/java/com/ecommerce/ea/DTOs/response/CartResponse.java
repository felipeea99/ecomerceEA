package com.ecommerce.ea.DTOs.response;

import com.ecommerce.ea.entities.Address;
import com.ecommerce.ea.entities.Cart;
import com.ecommerce.ea.entities.Customer;
import com.ecommerce.ea.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartResponse {
    private int cartId;
    private int quantity;
    private boolean isCompleted;
    private Product product;
    private Customer customer;

    public static CartResponse ToCartResponseObj(Cart cart) {
        return new CartResponse(
                cart.getCartId(),
                cart.getQuantity(),
                cart.isCompleted(),
                cart.getProduct(),
                cart.getCustomer()
        );
    }

}
