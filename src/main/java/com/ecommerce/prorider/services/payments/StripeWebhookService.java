package com.ecommerce.prorider.services.payments;

import com.ecommerce.prorider.entities.payments.Order;
import com.ecommerce.prorider.entities.payments.PaymentStatus;
import com.ecommerce.prorider.entities.store.StatusType;
import com.ecommerce.prorider.repository.payments.OrderRepository;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Account;
import com.stripe.exception.StripeException;
import org.springframework.stereotype.Service;


/**
 * Service to handle Stripe webhook events.
 * Processes relevant Stripe events to synchronize payment and onboarding status with the internal platform.
 */
@Service
public class StripeWebhookService {

    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final StoreService storeService;

    public StripeWebhookService(OrderService orderService, OrderRepository orderRepository, StoreService storeService) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
        this.storeService = storeService;
    }
    /**
     * Handles incoming Stripe webhook events and routes them to their specific processors.
     *
     * @param event the Stripe event received from the webhook.
     * @throws StripeException in case deserialization or Stripe API interaction fails.
     */
    public void handleEvent(Event event) throws StripeException {
        String eventType = event.getType();

        switch (eventType) {

            case "checkout.session.completed" -> {
                Session session = (Session) event.getDataObjectDeserializer()
                        .getObject()
                        .orElseThrow(() -> new RuntimeException("Invalid session object"));
                handleCheckoutCompleted(session);
            }

            case "payment_intent.succeeded" -> {
                PaymentIntent intent = (PaymentIntent) event.getDataObjectDeserializer()
                        .getObject()
                        .orElseThrow(() -> new RuntimeException("Invalid payment intent object"));
                handlePaymentSucceeded(intent);
            }

            case "account.updated" -> {
                Account account = (Account) event.getDataObjectDeserializer()
                        .getObject()
                        .orElseThrow(() -> new RuntimeException("Invalid account object"));
                handleAccountUpdated(account);
            }
            default -> {
                // Don't do anything or log the event
                System.out.println("Unhandled event type: " + eventType);
            }
        }
    }

    /**
     * Handles the event when a Stripe Checkout session is completed.
     * Retrieves the associated order using the PaymentIntent ID and updates its payment and status flags.
     *
     * @param session the Stripe Checkout session object containing the PaymentIntent ID.
     */
    private void handleCheckoutCompleted(Session session) {
        // Get the PaymentIntentId
        String paymentIntentId = session.getPaymentIntent();
        // Search the PaymentIntentId on the Order
       Order order =  orderService.findOrderByStripePaymentId(paymentIntentId);
        //paymentStatus
       order.setPaymentStatus(PaymentStatus.SUCCEEDED);
        //logistic status
       order.setStatus(StatusType.PREPARING);
       // Save the Changes
       orderRepository.save(order);
    }
    /**
     * Handles the event when a Stripe PaymentIntent is successfully completed.
     * This ensures redundancy in payment confirmation and order status update.
     *
     * @param intent the Stripe PaymentIntent object that succeeded.
     */
    private void handlePaymentSucceeded(PaymentIntent intent) {
        String paymentIntentId = intent.getId();
        Order order = orderService.findOrderByStripePaymentId(paymentIntentId);
        order.setPaymentStatus(PaymentStatus.SUCCEEDED);
        order.setStatus(StatusType.PREPARING);
        orderRepository.save(order);
        System.out.println("Payment completed: " + paymentIntentId);
    }
    /**
     * Handles updates to a Stripe connected account.
     * Specifically used to check whether onboarding is completed,
     * and if so, update the corresponding store entity in the platform.
     *
     * @param account the Stripe connected account object that was updated.
     */
    private void handleAccountUpdated(Account account) {
        String accountId = account.getId();
        boolean onboardingCompleted = account.getRequirements().getCurrentDeadline() == null;
        if (onboardingCompleted) {
            storeService.markOnboardingComplete(accountId);
            System.out.println("Onboarding completed for the account: " + accountId);
        } else {
            System.out.println("Account updated but onboarding still pending: " + accountId);
        }
    }
}
