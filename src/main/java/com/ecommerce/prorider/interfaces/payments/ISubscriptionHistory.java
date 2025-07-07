package com.ecommerce.prorider.interfaces.payments;

import com.ecommerce.prorider.DTOs.response.payments.SubscriptionHistoryResponse;
import com.ecommerce.prorider.entities.payments.SubscriptionDb;
import com.ecommerce.prorider.entities.payments.SubscriptionHistory;

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
