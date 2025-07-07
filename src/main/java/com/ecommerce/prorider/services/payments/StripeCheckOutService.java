package com.ecommerce.prorider.services.payments;

import com.ecommerce.prorider.DTOs.response.store.CartResponse;
import com.ecommerce.prorider.entities.store.Cart;
import com.ecommerce.prorider.services.store.CartService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.net.RequestOptions;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
/// Service that manages the creation of Stripe checkout sessions.
/// It receives a list of cart responses, fetches full product and quantity data,
/// and generates the session with the necessary line items for payment processing.
/// Supports integration with Stripe Connect connected accounts.
@Service
public class StripeCheckOutService {

    private final CartService cartService;

    public StripeCheckOutService(CartService cartService, @Value("${stripe.secret.key}") String secretKey) {
        this.cartService = cartService;
        Stripe.apiKey = secretKey;
    }

    public Session createCheckoutSession(String connectedAccountId, List<CartResponse> cartResponseList) throws StripeException {
        SessionCreateParams.Builder sessionBuilder = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setPaymentIntentData(
                        SessionCreateParams.PaymentIntentData.builder()
                                .setApplicationFeeAmount(0L) // commission is 0 for me
                                .build()
                )
                ///use the  ID or URL on the frontend to send the user to the checkout
//               .setSuccessUrl("") if you want it later
                .setReturnUrl("https://tusitio.com/checkout/return?session_id={CHECKOUT_SESSION_ID}");


        /// This will save all the cartIds from the CartResponseObject
        ///the cartId will be looked up on the database to get the full object.
        /// With that, we will be available to access to "cart.Product.productName" or "cart.Product.Price" etc
        List<Cart> cartList = cartResponseList.stream()
                .map(cartResponse -> cartService.findCartById(cartResponse.getCartId()))
                .filter(Objects::nonNull)
                .toList();

        Cart anyCart = cartList.get(0);
//        if (anyCart.getProduct().getStore() != null && !anyCart.getProduct().getStore().isOnboardingCompleted()) {
//            throw new IllegalStateException("El vendedor aún no ha completado el proceso de onboarding con Stripe.");
//        }


        /// Session Data LOOP
        for (Cart cart : cartList) {
            /// Get the Total
            int quantity = cart.getQuantity();
            BigDecimal price = cart.getProduct().getPrice();
            BigDecimal total = price.multiply(BigDecimal.valueOf(quantity));
            /// Data Initialization
            SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                    .setQuantity((long)quantity)  //Cart Quantity
                    .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                    .setCurrency("mxn")
                                    .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                    .setName(cart.getProduct().getProductName()) //ProductName
                                                    .build())
                                    .setUnitAmount(total.multiply(BigDecimal.valueOf(100)).longValueExact()) //get the total in centavos
                                    .build()
                    )
                    .build();

            sessionBuilder.addLineItem(lineItem);
        }

        RequestOptions requestOptions = RequestOptions.builder()
                .setStripeAccount(connectedAccountId)
                .build();

        return Session.create(sessionBuilder.build(), requestOptions);
    }
}