package com.ecommerce.ea.controllers.payments;

import com.ecommerce.ea.DTOs.request.payments.SubscriptionRequest;
import com.ecommerce.ea.DTOs.response.payments.SubscriptionResponse;
import com.ecommerce.ea.services.payments.SubscriptionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.util.UUID;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping
    public SubscriptionResponse createSubscription(@RequestBody SubscriptionRequest request) {
        return subscriptionService.createSubscription(request);
    }

    @PostMapping("/cancel/{storeId}")
    public SubscriptionResponse cancelSubscription(@PathVariable UUID storeId) {
        return subscriptionService.cancelSubscription(storeId);
    }

    @GetMapping("/{subscriptionId}")
    public SubscriptionResponse getSubscriptionById(@PathVariable UUID subscriptionId) {
        return subscriptionService.findSubscriptionById(subscriptionId);
    }

    @PostMapping("/webhook")
    public String handleWebhook(HttpServletRequest request,
                                @RequestHeader("Stripe-Signature") String sigHeader) {
        try {
            StringBuilder payload = new StringBuilder();
            try (BufferedReader reader = request.getReader()) {
                String line;
                while ((line = reader.readLine()) != null) {
                    payload.append(line);
                }
            }

            subscriptionService.handleWebhookEvent(payload.toString(), sigHeader);
            return "Webhook handled";
        } catch (Exception e) {
            return "Webhook error: " + e.getMessage();
        }
    }
}
