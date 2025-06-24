package com.ecommerce.ea.services.store;

import com.ecommerce.ea.DTOs.request.store.CartRequest;
import com.ecommerce.ea.DTOs.response.store.CartResponse;
import com.ecommerce.ea.DTOs.update.CartUpdate;
import com.ecommerce.ea.entities.auth.Customer;
import com.ecommerce.ea.entities.store.*;
import com.ecommerce.ea.exceptions.BadRequestException;
import com.ecommerce.ea.interfaces.store.ICart;
import com.ecommerce.ea.repository.store.AddressRepository;
import com.ecommerce.ea.repository.store.CartRepository;
import com.ecommerce.ea.repository.store.ShoppingHistoryRepository;
import com.ecommerce.ea.services.auth.CustomerService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class CartService implements ICart {

    private final CartRepository cartRepository;
    private final AddressRepository addressRepository;
    private final ShoppingHistoryRepository shoppingHistoryRepository;
    private final ProductService productService;
    private final CustomerService customerService;

    public CartService(CartRepository cartRepository, AddressRepository addressRepository, ShoppingHistoryRepository shoppingHistoryRepository, ProductService productService, CustomerService customerService){
        this.cartRepository  = cartRepository;
        this.addressRepository = addressRepository;
        this.shoppingHistoryRepository = shoppingHistoryRepository;
        this.productService = productService;
        this.customerService = customerService;
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
        // Product Inicialization
        Product product = productService.findProductByIdBaseForm(cartUpdate.getProductId());
        // Customer Inicialization
        Customer customer = customerService.findCustomerById(cartUpdate.getCustomerId());

       //Modify Changes
        cart.setCompleted(cartUpdate.isCompleted());
        cart.setCustomer(customer);
        cart.setProduct(product);
        cart.setQuantity(cartUpdate.getQuantity());
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
    public Void cartProcessCompleted(UUID customerId, int addressID, String purchaseUUID) {
        //Get the list of cartsObjects on the database by the customerId
         List<Cart> cartList = cartRepository.findAllCartsByCustomerId(customerId);
        //Get the Address to stores it on the Shopping History Object
        Address address = addressRepository.findById(addressID)
               .orElseThrow(() -> new BadRequestException("addressId was not found on the database"));
        //create the shoppingHistory objects to save it in Shopping History table
        for (Cart cart : cartList) {
            ShoppingHistory shObj = new ShoppingHistory();
            shObj.setDateTime(new Date());
            shObj.setCustomer(cart.getCustomer());
            shObj.setStatus(StatusType.PREPARING);
            shObj.setPurchaseUUID(purchaseUUID);
            shObj.setProduct(cart.getProduct());
            shObj.setQuantity(cart.getQuantity());
            shObj.setAddress(address);
            // Save ShoppingHistory
            shoppingHistoryRepository.save(shObj);
            // Delete cart
            cartRepository.deleteById(cart.getCartId());
        }
        return null;
    }


    @Override
    public Cart ToCartObj(CartRequest cartRequest) {
        /// Product Inicialization
        Product product = new Product();
        product = productService.findProductByIdBaseForm(cartRequest.getProductId());

        /// Customer Inicialization
        Customer customer = new Customer();
        customer = customerService.findCustomerById(cartRequest.getCustomerId());

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
        CartResponse cartResponse = new CartResponse();

        cartResponse.setCartId(cart.getCartId());
        cartResponse.setCompleted(cart.isCompleted());
        cartResponse.setQuantity(cart.getQuantity());
        cartResponse.setProductId(cart.getProduct().getProductId());
        cartResponse.setCartId(cart.getCartId());

        return cartResponse;
    }
}
