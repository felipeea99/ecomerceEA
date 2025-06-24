package com.ecommerce.ea.controllers.payments;

import com.ecommerce.ea.services.payments.StripeWebhookService;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/webhook")
public class StripeWebhookController {

    private final StripeWebhookService stripeWebhookService;

    @Value("${stripe.webhook.secret}")
    private String endpointSecret;

    public StripeWebhookController(StripeWebhookService stripeWebhookService) {
        this.stripeWebhookService = stripeWebhookService;
    }

    @PostMapping
    public String handleWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
        try {
            // Verifica el evento con la firma
            Event event = Webhook.constructEvent(payload, sigHeader, endpointSecret);

            // Procesa el evento
            stripeWebhookService.handleEvent(event);

            return "Webhook processed";
        } catch (Exception e) {
            // Puede ser firma inválida, error de Stripe, etc.
            System.out.println("Webhook error: " + e.getMessage());
            return "Webhook failed: " + e.getMessage();
        }
    }
}
