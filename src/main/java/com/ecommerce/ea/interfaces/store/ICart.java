package com.ecommerce.ea.interfaces.store;

import com.ecommerce.ea.DTOs.request.store.CartRequest;
import com.ecommerce.ea.DTOs.response.store.CartResponse;
import com.ecommerce.ea.DTOs.update.CartUpdate;
import com.ecommerce.ea.entities.store.Cart;


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
