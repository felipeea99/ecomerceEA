package com.ecommerce.ea.interfaces;

import com.ecommerce.ea.DTOs.request.CartRequest;
import com.ecommerce.ea.DTOs.response.CartResponse;
import com.ecommerce.ea.DTOs.update.CartUpdate;
import com.ecommerce.ea.entities.Cart;


import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface ICart {

    Cart findCartById(int cartId);
    CartResponse addCart(CartRequest cartRequest);
    CartResponse editCart(CartUpdate cartUpdate);
    Boolean deleteCart(int cartID);
    List<CartResponse> findAllItemsInCartByCustomerId(UUID userId);
    Void operationsInCart(int cartID, boolean isIncrement);
    Void cartProcessCompleted(UUID customerId , int addressID, String PurchaseUUID);
    Cart ToCartObj(CartRequest cartRequest);
    CartResponse ToCartResponse(Cart cart);
}
