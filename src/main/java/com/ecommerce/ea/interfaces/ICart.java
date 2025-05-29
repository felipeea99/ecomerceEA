package com.ecommerce.ea.interfaces;

import com.ecommerce.ea.DTOs.request.CartRequest;
import com.ecommerce.ea.DTOs.response.CartResponse;
import com.ecommerce.ea.DTOs.update.CartUpdate;
import com.ecommerce.ea.entities.Cart;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface ICart {

    CompletableFuture<CartResponse> AddCart(CartRequest cartRequest);
    CompletableFuture<CartResponse> EditCart(CartUpdate cartUpdate);
    CompletableFuture<Boolean> DeleteCart(int cartID);
    CompletableFuture<List<CartResponse>> GetAllItemsInCartByUserId(UUID userId);
    CompletableFuture<Void> OperationsInCart(int cartID, boolean isIncrement);
    CompletableFuture<Void> CartProcessCompleted(UUID userId , int addressID);
}
