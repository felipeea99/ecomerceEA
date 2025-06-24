package com.ecommerce.ea.controllers.payments;

import com.ecommerce.ea.DTOs.response.store.CartResponse;
import com.ecommerce.ea.services.payments.StripeCheckOutService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/checkout")
public class StripeCheckOutController {
    private final StripeCheckOutService stripeCheckOutService;

    public StripeCheckOutController(StripeCheckOutService stripeCheckOutService) {
        this.stripeCheckOutService = stripeCheckOutService;
    }

    /// POST: Create a checkout session for the provided cart and connected account
    @PostMapping("/session/{connectedAccountId}")
    public Session createSession(
            @PathVariable String connectedAccountId,
            @RequestBody List<CartResponse> cartResponseList
    ) throws StripeException {
        return stripeCheckOutService.createCheckoutSession(connectedAccountId, cartResponseList);
    }
}
