package com.ecommerce.ea.interfaces;

import com.ecommerce.ea.entities.Cart;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface ICart {

    CompletableFuture<Cart> addCart(Cart cart);
    CompletableFuture<Cart> editCart(Cart cartUpdate);
    CompletableFuture<Boolean> deleteCart(int cartID);
    CompletableFuture<List<Cart>> getAllItemsInCartByUserId(UUID userId);
    CompletableFuture<Void> operationsInCart(int cartID, boolean isIncrement);
    CompletableFuture<Void> cartProcessCompleted(UUID userId , int addressID);
}
