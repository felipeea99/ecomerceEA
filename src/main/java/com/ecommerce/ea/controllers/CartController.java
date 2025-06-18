package com.ecommerce.ea.controllers;

import com.ecommerce.ea.AOP_Functions.annotations.ValidateStoreAccess;
import com.ecommerce.ea.AOP_Functions.context.StoreContextHolder;
import com.ecommerce.ea.DTOs.request.CartRequest;
import com.ecommerce.ea.DTOs.response.CartResponse;
import com.ecommerce.ea.DTOs.update.CartUpdate;
import com.ecommerce.ea.services.CartService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    /// This EndPoint adds a cartObject into the database
    @ValidateStoreAccess
    @PostMapping("/{storeName}/{userId}")
    public CartResponse addCart(CartRequest cartRequest){
        UUID customerId = StoreContextHolder.getCustomerId();
        cartRequest.setCustomerId(customerId);
        return this.cartService.addCart(cartRequest);
    }

    /// This EndPoint edits a cartObject by using the cartId
    @ValidateStoreAccess
    @PutMapping("/{storeName}/{userId}")
    public CartResponse editCart(CartUpdate cartUpdate){
        return this.cartService.editCart(cartUpdate);
    }

    /// This EndPoint deletes a cartObject by using the cartId
    @ValidateStoreAccess
    @DeleteMapping("/{storeName}/{userId}/{cartId}")
    public Boolean deleteCart(int cartId){
        return this.cartService.deleteCart(cartId);
    }

    /// This EndPoint retrieves all the cartObjects in the cart by using the UserId
    @ValidateStoreAccess
    @GetMapping("/{storeName}/{userId}")
    public List<CartResponse> findAllItemsInCartByUserId(UUID userId){
        /// Retrieve the customerId from the context provided by @ValidateStoreAccess
        UUID customerId = StoreContextHolder.getCustomerId();
        return this.cartService.findAllItemsInCartByCustomerId(customerId);
    }

}
