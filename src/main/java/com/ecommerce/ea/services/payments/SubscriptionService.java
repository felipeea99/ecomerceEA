package com.ecommerce.ea.services.payments;

import com.ecommerce.ea.DTOs.request.payments.SubscriptionRequest;
import com.ecommerce.ea.DTOs.response.payments.SubscriptionResponse;
import com.ecommerce.ea.entities.auth.Store;
import com.ecommerce.ea.entities.payments.SubscriptionDb;
import com.ecommerce.ea.entities.payments.StripeSubscriptionStatus;
import com.ecommerce.ea.interfaces.payments.ISubscription;
import com.ecommerce.ea.repository.auth.StoreRepository;
import com.ecommerce.ea.repository.payments.SubscriptionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.Subscription;
import com.stripe.net.Webhook;
import com.stripe.param.SubscriptionCreateParams;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Map;
import java.util.UUID;

@Service
public class SubscriptionService implements ISubscription {

    private final SubscriptionRepository subscriptionRepository;
    private final StoreRepository storeRepository;

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    @Value("${stripe.webhook.secret}")
    private String stripeWebhookSecret;

    public SubscriptionService(SubscriptionRepository subscriptionRepository, StoreRepository storeRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.storeRepository = storeRepository;
    }

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeSecretKey;
    }

    @Override
    public SubscriptionResponse createSubscription(SubscriptionRequest subscriptionRequest) {
        try {
            // Crear la subscripción en Stripe
            Subscription stripeSub = Subscription.create(
                    SubscriptionCreateParams.builder()
                            .setCustomer(subscriptionRequest.getStripeCustomerId())
                            .addItem(
                                    SubscriptionCreateParams.Item.builder()
                                            .setPrice(subscriptionRequest.getStripePriceId())
                                            .build()
                            )
                            .build()
            );

            Store store = storeRepository.findById(subscriptionRequest.getStoreId())
                    .orElseThrow(() -> new RuntimeException("Store not found"));

            LocalDate startDate = Instant.ofEpochSecond(stripeSub.getStartDate())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            LocalDate endDate = stripeSub.getStartDate() != null
                    ? Instant.ofEpochSecond(stripeSub.getEndedAt())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
                    : null;

            // Crear entidad local
            SubscriptionDb newSubscription = new SubscriptionDb();
            newSubscription.setSubscriptionId(UUID.randomUUID());
            newSubscription.setStripeCustomerId(subscriptionRequest.getStripeCustomerId());
            newSubscription.setStripePriceId(subscriptionRequest.getStripePriceId());
            newSubscription.setStripeSubscriptionId(stripeSub.getId()); // ID de Stripe
            newSubscription.setStatus(StripeSubscriptionStatus.fromStripe(stripeSub.getStatus()));
            newSubscription.setStartDate(startDate);
            newSubscription.setEndDate(endDate);
            newSubscription.setStore(store);

            subscriptionRepository.save(newSubscription);

            return new SubscriptionResponse(
                    newSubscription.getStatus().name(),
                    newSubscription.getStartDate(),
                    newSubscription.getEndDate(),
                    newSubscription.getStore().getStoreName()
            );
        } catch (StripeException e) {
            throw new RuntimeException("Error creating subscription with Stripe", e);
        }
    }

    @Override
    public SubscriptionResponse cancelSubscription(UUID storeId) {
        SubscriptionDb subscription = subscriptionRepository.findByStore_StoreId(storeId)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));

        try {
            Subscription stripeSub = Subscription.retrieve(subscription.getStripeSubscriptionId());
            stripeSub.cancel();

            subscription.setStatus(StripeSubscriptionStatus.CANCELED);
            subscription.setEndDate(LocalDate.now());
            subscriptionRepository.save(subscription);

            return new SubscriptionResponse(
                    subscription.getStatus().name(),
                    subscription.getStartDate(),
                    subscription.getEndDate(),
                    subscription.getStore().getStoreName()
            );
        } catch (StripeException e) {
            throw new RuntimeException("Error cancelling subscription", e);
        }
    }

    @Override
    public SubscriptionResponse findSubscriptionById(UUID subscriptionId) {
        SubscriptionDb subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));

        return new SubscriptionResponse(
                subscription.getStatus().name(),
                subscription.getStartDate(),
                subscription.getEndDate(),
                subscription.getStore().getStoreName()
        );
    }

    @Override
    public SubscriptionDb findSubscriptionByIdBaseForm(UUID subscriptionId) {
        return subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));
    }

    @Override
    public void handleWebhookEvent(String payload, String sigHeader) {
        try {
            Event event = Webhook.constructEvent(payload, sigHeader, stripeWebhookSecret);

            Subscription stripeSub = (Subscription) event.getDataObjectDeserializer()
                    .getObject()
                    .orElseThrow(() -> new RuntimeException("Invalid webhook data"));

            String stripeId = stripeSub.getId();
            SubscriptionDb sub = subscriptionRepository.findByStripeSubscriptionId(stripeId).orElse(null);

            if (sub != null) {
                switch (event.getType()) {
                    case "customer.subscription.deleted":
                        sub.setStatus(StripeSubscriptionStatus.CANCELED);
                        sub.setEndDate(LocalDate.now());
                        break;
                    case "customer.subscription.updated":
                        sub.setStatus(StripeSubscriptionStatus.fromStripe(stripeSub.getStatus()));

                        // Parseamos el JSON a Map para obtener current_period_end
                        ObjectMapper mapper = new ObjectMapper();
                        Map<String, Object> jsonMap = mapper.readValue(stripeSub.getObject(), Map.class);

                        Long currentPeriodEndUnix = null;
                        if (jsonMap.containsKey("current_period_end")) {
                            // current_period_end puede venir como Integer o Long dependiendo del JSON
                            Object value = jsonMap.get("current_period_end");
                            if (value instanceof Integer) {
                                currentPeriodEndUnix = ((Integer) value).longValue();
                            } else if (value instanceof Long) {
                                currentPeriodEndUnix = (Long) value;
                            }
                        }

                        LocalDate newEndDate = currentPeriodEndUnix != null
                                ? Instant.ofEpochSecond(currentPeriodEndUnix)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                                : null;

                        sub.setEndDate(newEndDate);
                        break;
                }
                subscriptionRepository.save(sub);
            }
        } catch (Exception e) {
            throw new RuntimeException("Invalid Stripe webhook", e);
        }
    }

}