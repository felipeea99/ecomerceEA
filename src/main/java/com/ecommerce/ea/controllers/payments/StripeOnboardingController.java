package com.ecommerce.ea.controllers.payments;

import com.ecommerce.ea.services.payments.StripeOnboardingService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/onboarding")
public class StripeOnboardingController {

    private final StripeOnboardingService stripeService;

    public StripeOnboardingController(StripeOnboardingService stripeService) {
        this.stripeService = stripeService;
    }

    @PostMapping("/account/{storeId}")
    public Map<String, String> createAccount(@PathVariable UUID storeId) {
        try {
            String accountId = stripeService.createConnectedAccount(storeId);
            return Map.of("account", accountId);
        } catch (Exception e) {
            return Map.of("error", e.getMessage());
        }
    }

    @PostMapping("/account-session")
    public Map<String, String> createSession(@RequestBody Map<String, String> req) {
        try {
            String session = stripeService.createAccountSession(req.get("account"));
            return Map.of("client_secret", session);
        } catch (Exception e) {
            return Map.of("error", e.getMessage());
        }
    }

    @PostMapping("/charge")
    public Map<String, String> chargeConnectedAccount(@RequestBody Map<String, String> req) {
        try {
            String accountId = req.get("account");
            Long amount = Long.parseLong(req.get("amount"));
            String currency = req.get("currency");
            String paymentMethodId = req.get("paymentMethodId");

            String chargeId = stripeService.createDirectCharge(accountId, amount, currency, paymentMethodId);
            return Map.of("payment_intent_id", chargeId);
        } catch (Exception e) {
            return Map.of("error", e.getMessage());
        }
    }
}
