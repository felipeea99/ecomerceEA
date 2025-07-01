package com.ecommerce.ea.controllers.store;

import com.ecommerce.ea.AOP_Functions.annotations.ValidateStoreAccess;
import com.ecommerce.ea.AOP_Functions.context.StoreContextHolder;
import com.ecommerce.ea.DTOs.request.store.CartRequest;
import com.ecommerce.ea.DTOs.response.store.CartResponse;
import com.ecommerce.ea.DTOs.update.store.CartUpdate;
import com.ecommerce.ea.services.store.CartService;
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
    public CartResponse addCart(@RequestBody CartRequest cartRequest){
        UUID customerId = StoreContextHolder.getCustomerId();
        cartRequest.setCustomerId(customerId);
        return this.cartService.addCart(cartRequest);
    }

    /// This EndPoint edits a cartObject by using the cartId
    @ValidateStoreAccess
    @PutMapping("/{storeName}/{userId}")
    public CartResponse editCart(@RequestBody CartUpdate cartUpdate){
        return this.cartService.editCart(cartUpdate);
    }

    /// This EndPoint deletes a cartObject by using the cartId
    @ValidateStoreAccess
    @DeleteMapping("/{storeName}/{userId}/{cartId}")
    public Boolean deleteCart(@PathVariable int cartId){
        return this.cartService.deleteCart(cartId);
    }

    /// This EndPoint retrieves all the cartObjects in the cart by using the UserId
    @ValidateStoreAccess
    @GetMapping("/{storeName}/{userId}")
    public List<CartResponse> findAllItemsInCartByUserId(@PathVariable UUID userId){
        /// Retrieve the customerId from the context provided by @ValidateStoreAccess
        UUID customerId = StoreContextHolder.getCustomerId();
        return this.cartService.findAllItemsInCartByCustomerId(customerId);
    }

    @PutMapping("/operations/{cartId}/{isIncrement}")
    public void operationsInCart(@PathVariable int cartId, @PathVariable boolean isIncrement) {
        cartService.operationsInCart(cartId, isIncrement);
    }

}
