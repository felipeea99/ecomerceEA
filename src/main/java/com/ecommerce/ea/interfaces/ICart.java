package com.ecommerce.ea.interfaces;

import com.ecommerce.ea.DTOs.request.CartRequest;
import com.ecommerce.ea.DTOs.response.CartResponse;
import com.ecommerce.ea.DTOs.update.CartUpdate;
import com.ecommerce.ea.entities.Cart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface ICart {

    Mono<CartResponse> AddCart(CartRequest cartRequest);
    Mono<CartResponse> EditCart(CartUpdate cartUpdate);
    Mono<Boolean> DeleteCart(int cartID);
    Mono<List<CartResponse>> GetAllItemsInCartByUserId(UUID userId);
    Mono<Void> OperationsInCart(int cartID, boolean isIncrement);
    Mono<Void> CartProcessCompleted(UUID userId , int addressID);
}
