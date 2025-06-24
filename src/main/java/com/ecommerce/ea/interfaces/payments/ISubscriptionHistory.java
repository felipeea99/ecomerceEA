package com.ecommerce.ea.interfaces.payments;

import com.ecommerce.ea.DTOs.response.payments.SubscriptionHistoryResponse;
import com.ecommerce.ea.DTOs.response.payments.SubscriptionResponse;
import com.ecommerce.ea.entities.payments.SubscriptionDb;
import com.ecommerce.ea.entities.payments.SubscriptionHistory;

import java.util.List;
import java.util.UUID;

public interface ISubscriptionHistory {
    SubscriptionHistory findSubscriptionById(int subscriptionHistoryId);
    SubscriptionHistory addSubscriptionHistory (SubscriptionDb subscription);
    Boolean deleteSubscriptionHistory(int subscriptionHistoryId);
    List<SubscriptionHistoryResponse> findAllSubscriptionHistory ();
    List<SubscriptionHistoryResponse> findAllSubscriptionHistoryByStoreId (UUID storeId);
    SubscriptionHistoryResponse ToSubscriptionHistoryResponse(SubscriptionHistory subscriptionHistory);
}
