package com.ecommerce.ea.interfaces.payments;

import com.ecommerce.ea.DTOs.request.payments.SubscriptionRequest;
import com.ecommerce.ea.DTOs.response.payments.SubscriptionResponse;
import com.ecommerce.ea.entities.payments.SubscriptionDb;

import java.util.UUID;

public interface ISubscription {
    SubscriptionResponse createSubscription (SubscriptionRequest subscriptionRequest);
    SubscriptionResponse cancelSubscription (UUID storeId);
    SubscriptionResponse findSubscriptionById (UUID subscriptionId);
    SubscriptionDb findSubscriptionByIdBaseForm (UUID subscriptionId);
    void handleWebhookEvent(String payload, String sigHeader);

}
