package com.ecommerce.prorider.interfaces.store;

import com.ecommerce.prorider.DTOs.request.store.CartRequest;
import com.ecommerce.prorider.DTOs.response.store.CartResponse;
import com.ecommerce.prorider.DTOs.update.store.CartUpdate;
import com.ecommerce.prorider.entities.store.Cart;


import java.util.List;
import java.util.UUID;

public interface ICart {

    Cart findCartById(int cartId);
    CartResponse addCart(CartRequest cartRequest);
    CartResponse editCart(CartUpdate cartUpdate);
    Boolean deleteCart(int cartID);
    List<CartResponse> findAllItemsInCartByCustomerId(UUID userId);
    Void operationsInCart(int cartID, boolean isIncrement);
    Void cartProcessCompleted(UUID customerId);
    Cart ToCartObj(CartRequest cartRequest);
    CartResponse ToCartResponse(Cart cart);
}
