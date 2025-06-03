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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    public Mono<CartResponse> AddCart(CartRequest cartRequest) {
        //Convert it to Address Object
        Cart cart = cartRequest.ToCartObj();
       //Save the cartObj and stores it on the variable
        Mono<Cart> cartObj = cartRepository.save(cart);
        //Convert it to CartResponse
       return cartObj.map(CartResponse::ToCartResponseObj);
    }

    @Override
    public Mono<CartResponse> EditCart(CartUpdate cartUpdate) {
        //Search the cartId
       return cartRepository.findById(cartUpdate.getCartId()).switchIfEmpty(Mono.error(new BadRequestException("CartId was not found on the database"))) //search the cartId on the database
               .flatMap(cart ->{
                   //Modify Changes
                   cart.setCompleted(cartUpdate.isCompleted());
                   cart.setCustomer(cartUpdate.getCustomer());
                   cart.setProduct(cartUpdate.getProduct());
                   cart.setQuantity(cartUpdate.getQuantity());
                   //save the object
                   return cartRepository.save(cart);
               })
               //Convert it to CartResponse
               .map(CartResponse::ToCartResponseObj);
    }

    @Override
    public Mono<Boolean> DeleteCart(int cartID) {
          return cartRepository.deleteById(cartID).thenReturn(true).onErrorReturn(false);
    }

    @Override
    public Mono<List<CartResponse>> GetAllItemsInCartByUserId(UUID userId) {
        //userId validation
        userRepository.findById(userId).switchIfEmpty(Mono.error(new BadRequestException("UserId was not found on the database")));

        return cartRepository.findAllCartsByUserId(userId)
                .map(carts ->
                        carts.stream().map(CartResponse::ToCartResponseObj).collect(Collectors.toList()));
    }

    @Override
    public Mono<Void> OperationsInCart(int cartID, boolean isIncrement) {
        return cartRepository.findById(cartID)
                .switchIfEmpty(Mono.error(new BadRequestException("cartId was not found on the database"))) //cartId validation
        .flatMap(cart -> {
            if (!isIncrement) {
                if (cart.getQuantity() > 1) {
                    cart.setQuantity(cart.getQuantity() - 1);

                    CartUpdate update = new CartUpdate();
                    update.setCartId(cart.getCartId());
                    update.setQuantity(cart.getQuantity());
                    update.setCompleted(cart.isCompleted());
                    update.setCustomer(cart.getCustomer());
                    update.setProduct(cart.getProduct());

                    return EditCart(update).then();
                } else {
                    return DeleteCart(cartID).then();
                }
            } else {
                cart.setQuantity(cart.getQuantity() + 1);
                CartUpdate update = new CartUpdate();
                update.setCartId(cart.getCartId());
                update.setQuantity(cart.getQuantity());
                update.setCompleted(cart.isCompleted());
                update.setCustomer(cart.getCustomer());
                update.setProduct(cart.getProduct());

                return EditCart(update).then();
            }
        });
    }

    @Override
    public Mono<Void> CartProcessCompleted(UUID userId, int addressID) {
        //userId validation
        userRepository.findById(userId).switchIfEmpty(Mono.error(new BadRequestException("userId was not found on the database")));
        //Get the list of cartsObjects on the database by the userId
        cartRepository.findAllCartsByUserId(userId);
        //Get the Address to stores it on the Shopping History Object
       Mono<Address> address = addressRepository.findById(addressID).switchIfEmpty(Mono.error(new BadRequestException("userId was not found on the database")));
        //Stored the CartObjects on the Shopping History table

        //Delete cartObjects


        return null;
    }

}
