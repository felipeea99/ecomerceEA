package com.ecommerce.ea.services;

import com.ecommerce.ea.DTOs.request.CartRequest;
import com.ecommerce.ea.DTOs.response.CartResponse;
import com.ecommerce.ea.DTOs.update.CartUpdate;
import com.ecommerce.ea.entities.Address;
import com.ecommerce.ea.entities.Cart;
import com.ecommerce.ea.exceptions.BadRequestException;
import com.ecommerce.ea.interfaces.ICart;
import com.ecommerce.ea.repository.AddressRepository;
import com.ecommerce.ea.repository.CartRepository;
import com.ecommerce.ea.repository.UserRepository;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class CartService implements ICart {

    private  final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    public CartService(CartRepository cartRepository, UserRepository userRepository, AddressRepository addressRepository){
        this.cartRepository  = cartRepository;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    public CompletableFuture<CartResponse> AddCart(CartRequest cartRequest) {
        //Convert it to Address Object
        Cart cart = cartRequest.ToCartObj();
       //Save the cartObj and stores it on the variable
        Cart cartObj = cartRepository.save(cart);
        //Convert it to CartResponse
        CartResponse cartResponse = CartResponse.ToCartResponseObj(cartObj);

       return CompletableFuture.completedFuture(cartResponse);
    }

    @Override
    public CompletableFuture<CartResponse> EditCart(CartUpdate cartUpdate) {
        //Search the cartId
       Cart cartObj = cartRepository.findById(cartUpdate.getCartId()).orElseThrow(() -> new BadRequestException("CartId was not found on the database"));
        //Modify Changes
       cartObj.setCompleted(cartObj.isCompleted());
       cartObj.setCustomer(cartObj.getCustomer());
       cartObj.setProduct(cartObj.getProduct());
       cartObj.setQuantity(cartObj.getQuantity());
        //Save changes
      Cart cartSaved = cartRepository.save(cartObj);
        //Convert it to CartResponse
        CartResponse cartResponse = CartResponse.ToCartResponseObj(cartSaved);

        return CompletableFuture.completedFuture(cartResponse);
    }

    @Override
    public CompletableFuture<Boolean> DeleteCart(int cartID) {
        try{
            cartRepository.deleteById(cartID);
            return CompletableFuture.completedFuture(true);
        }catch (Exception e){
            return CompletableFuture.completedFuture(false);
        }
    }

    @Override
    public CompletableFuture<List<CartResponse>> GetAllItemsInCartByUserId(UUID userId) {
        //userId validation
        userRepository.findById(userId).orElseThrow(() -> new BadRequestException("userId was not found on the database"));

        return cartRepository.findAllCartsByUserId(userId)
                .thenApply(carts ->
                        carts.stream()
                                .map(CartResponse::ToCartResponseObj)
                                .collect(Collectors.toList()));
    }

    @Override
    public CompletableFuture<Void> OperationsInCart(int cartID, boolean isIncrement) {
        return CompletableFuture.supplyAsync(() -> cartRepository.findById(cartID)
                .orElseThrow(() -> new BadRequestException("CartId was not found on the database"))
        ).thenCompose(cart -> {
            if (!isIncrement) {
                if (cart.getQuantity() > 1) {
                    cart.setQuantity(cart.getQuantity() - 1);

                    CartUpdate update = new CartUpdate();
                    update.setCartId(cart.getCartId());
                    update.setQuantity(cart.getQuantity());
                    update.setCompleted(cart.isCompleted());
                    update.setCustomer(cart.getCustomer());
                    update.setProduct(cart.getProduct());

                    return EditCart(update).thenApply(c -> null);
                } else {
                    return DeleteCart(cartID).thenApply(b -> null);
                }
            } else {
                cart.setQuantity(cart.getQuantity() + 1);
                CartUpdate update = new CartUpdate();
                update.setCartId(cart.getCartId());
                update.setQuantity(cart.getQuantity());
                update.setCompleted(cart.isCompleted());
                update.setCustomer(cart.getCustomer());
                update.setProduct(cart.getProduct());

                return EditCart(update).thenApply(c -> null);
            }
        });
    }

    @Override
    public CompletableFuture<Void> CartProcessCompleted(UUID userId, int addressID) {
        //userId validation
        userRepository.findById(userId).orElseThrow(() -> new BadRequestException("userId was not found on the database"));
        //Get the list of cartsObjects on the database by the userId
         cartRepository.findAllCartsByUserId(userId);
        //Get the Address to stores it on the Shopping History Object
        Address address = addressRepository.findById(addressID)
                .orElseThrow(() -> new BadRequestException("AddressID was not found on the database"));
        //Stored the CartObjects on the Shopping History table

        //Delete cartObjects


        return null;
    }

}
