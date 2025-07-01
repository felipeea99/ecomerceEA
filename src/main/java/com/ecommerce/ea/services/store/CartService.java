package com.ecommerce.ea.services.store;

import com.ecommerce.ea.DTOs.request.store.CartRequest;
import com.ecommerce.ea.DTOs.response.store.CartResponse;
import com.ecommerce.ea.DTOs.response.store.ProductResponse;
import com.ecommerce.ea.DTOs.response.store.SizeResponse;
import com.ecommerce.ea.DTOs.update.store.CartUpdate;
import com.ecommerce.ea.entities.auth.Customer;
import com.ecommerce.ea.entities.store.*;
import com.ecommerce.ea.exceptions.BadRequestException;
import com.ecommerce.ea.interfaces.store.ICart;
import com.ecommerce.ea.repository.store.CartRepository;
import com.ecommerce.ea.services.auth.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CartService implements ICart {

    private final CartRepository cartRepository;
    private final ProductService productService;
    private final CustomerService customerService;
    private final SizeService sizeService;

    public CartService(CartRepository cartRepository, ProductService productService, CustomerService customerService, SizeService sizeService){
        this.cartRepository  = cartRepository;
        this.productService = productService;
        this.customerService = customerService;
        this.sizeService = sizeService;
    }

    @Override
    public Cart findCartById(int cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new BadRequestException("cartId was not found on the database"));
    }

    @Override
    public CartResponse addCart(CartRequest cartRequest) {
        //Convert it to Address Object
        Cart cart = this.ToCartObj(cartRequest);
        //Save the cartObj and stores it on the variable
        Cart cartObj = cartRepository.save(cart);
        //Convert it to CartResponse
        return this.ToCartResponse(cartObj);

    }

    @Override
    public CartResponse editCart(CartUpdate cartUpdate) {
        //Search the cartId
       Cart cart = this.findCartById(cartUpdate.getCartId());
        // Product Initialization
        Product product = productService.findProductByIdBaseForm(cartUpdate.getProductId());
        // Customer Initialization
        Customer customer = customerService.findCustomerById(cartUpdate.getCustomerId());
        // SizeResponse Initialization
        Size size = sizeService.ToSizeObject(cartUpdate.getSizeObj());
       //Modify Changes
        cart.setCompleted(cartUpdate.isCompleted());
        cart.setCustomer(customer);
        cart.setProduct(product);
        cart.setQuantity(cartUpdate.getQuantity());
        cart.setSizeObj(size);
        cart.setSize(cartUpdate.isSize());

        //save the object in the database and stored on the "cartSaved" object
        Cart cartSaved = cartRepository.save(cart);
        //Convert it from "Cart" type to "CartResponse"
        return this.ToCartResponse(cartSaved);
    }

    @Override
    public Boolean deleteCart(int cartID) {
        //CartId Validation
        this.findCartById(cartID);
        //Delete Cart Obj from the database
        cartRepository.deleteById(cartID);
        return true;
    }

    @Override
    public List<CartResponse> findAllItemsInCartByCustomerId(UUID customerId) {
        //find all the carts object on the database base on the customerId
        List<Cart> cartList = cartRepository.findAllCartsByCustomerId(customerId);
        //Convert the cartList from "Cart" type into "CartResponse"
       return cartList.stream().map(this::ToCartResponse).toList();
    }

    @Override
    public Void operationsInCart(int cartID, boolean isIncrement) {
        //CartId validation
        Cart cart = this.findCartById(cartID);

        // is it increment or decrement
        if (!isIncrement) {
            if (cart.getQuantity() > 1) {
                //decrement quantity
                CartUpdate update = new CartUpdate();
                update.setCartId(cart.getCartId());
                update.setQuantity(cart.getQuantity());
                update.setCompleted(cart.isCompleted());
                update.setCustomerId(cart.getCustomer().getCustomerId());
                update.setProductId(cart.getProduct().getProductId());
                // Call the editCart method
                editCart(update);
            } else {
                // if quantity is 0 or less will be deleted
                 deleteCart(cartID);
            }
        } else {
            // Increment the quantity by 1
            cart.setQuantity(cart.getQuantity() + 1);

            CartUpdate update = new CartUpdate();
            update.setCartId(cart.getCartId());
            update.setQuantity(cart.getQuantity());
            update.setCompleted(cart.isCompleted());
            update.setCustomerId(cart.getCustomer().getCustomerId());
            update.setProductId(cart.getProduct().getProductId());
            // Call the editCart method
            editCart(update);
        }
        return null;
    }

    @Override
    public Void cartProcessCompleted(UUID customerId) {
        ///Get the list of cartsObjects on the database by the customerId
         List<Cart> cartList = cartRepository.findAllCartsByCustomerId(customerId);
            for (Cart cart : cartList){
            /// Delete cart
            cartRepository.deleteById(cart.getCartId());
        }
        return null;
    }


    @Override
    public Cart ToCartObj(CartRequest cartRequest) {
        /// Product Initialization
       Product product = productService.findProductByIdBaseForm(cartRequest.getProductId());

        /// Customer Initialization
        Customer customer = customerService.findCustomerById(cartRequest.getCustomerId());

        /// Cart Build
        Cart cart = new Cart();
        cart.setQuantity(cartRequest.getQuantity());
        cart.setCompleted(cartRequest.isCompleted());
        cart.setProduct(product);
        cart.setCustomer(customer);
        return cart;
    }

    @Override
    public CartResponse ToCartResponse(Cart cart) {
        /// SizeResponse Initialization
       SizeResponse size = sizeService.ToSizeResponse(cart.getSizeObj());
        /// Product Initialization
       ProductResponse productResponse = productService.ToProductResponse(cart.getProduct());
        /// Cart Initialization
        CartResponse cartResponse = new CartResponse();
        cartResponse.setCartId(cart.getCartId());
        cartResponse.setCompleted(cart.isCompleted());
        cartResponse.setQuantity(cart.getQuantity());
        cartResponse.setProduct(productResponse);
        cartResponse.setSize(cart.isSize());
        cartResponse.setSizeObj(size);

        cartResponse.setCartId(cart.getCartId());

        return cartResponse;
    }
}
