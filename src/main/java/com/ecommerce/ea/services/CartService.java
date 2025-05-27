package com.ecommerce.ea.services;

import com.ecommerce.ea.entities.Cart;
import com.ecommerce.ea.exceptions.BadRequestException;
import com.ecommerce.ea.interfaces.ICart;
import com.ecommerce.ea.repository.CartRepository;
import com.ecommerce.ea.repository.UserRepository;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class CartService implements ICart {

    private  final CartRepository cartRepository;
    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository, UserRepository userRepository){
        this.cartRepository  = cartRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CompletableFuture<Cart> addCart(Cart cart) {
       Cart cartObj = cartRepository.save(cart);
       return CompletableFuture.completedFuture(cartObj);
    }

    @Override
    public CompletableFuture<Cart> editCart(Cart cartUpdate) {

       Cart cartObj = cartRepository.findById(cartUpdate.getCartId()).orElseThrow(() -> new BadRequestException("CartId was not found on the database"));

       cartObj.setCompleted(cartObj.isCompleted());
       cartObj.setCustomer(cartObj.getCustomer());
       cartObj.setProduct(cartObj.getProduct());
       cartObj.setQuantity(cartObj.getQuantity());

       cartRepository.save(cartObj);

        return CompletableFuture.completedFuture(cartObj);
    }

    @Override
    public CompletableFuture<Boolean> deleteCart(int cartID) {
        try{
            cartRepository.deleteById(cartID);
            return CompletableFuture.completedFuture(true);
        }catch (Exception e){
            return CompletableFuture.completedFuture(false);
        }
    }

    @Override
    public CompletableFuture<List<Cart>> getAllItemsInCartByUserId(UUID userId) {
        //userId validation
        userRepository.findById(userId).orElseThrow(() -> new BadRequestException("userId was not found on the database"));

        return cartRepository.findAllCartsByUserId(userId);
    }

    @Override
    public CompletableFuture<Void> operationsInCart(int cartID, boolean isIncrement) {
        return null;
    }

    @Override
    public CompletableFuture<Void> cartProcessCompleted(UUID userId, int addressID) {
        return null;
    }
}
