package com.ecommerce.ea.interfaces.payments;

import com.ecommerce.ea.DTOs.request.payments.SubscriptionRequest;
import com.ecommerce.ea.DTOs.response.payments.SubscriptionDbResponse;
import com.ecommerce.ea.entities.payments.SubscriptionDb;

import java.util.UUID;

public interface ISubscription {
    SubscriptionDbResponse createSubscription (SubscriptionRequest subscriptionRequest);
    SubscriptionDbResponse cancelSubscription (UUID storeId);
    SubscriptionDbResponse findSubscriptionById (UUID subscriptionId);
    SubscriptionDb findSubscriptionByIdBaseForm (UUID subscriptionId);
    void handleWebhookEvent(String payload, String sigHeader);

    SubscriptionDbResponse TOSubscriptionDbResponse(SubscriptionDb subscriptionDb);
}
