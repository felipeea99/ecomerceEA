package com.ecommerce.prorider.interfaces.payments;

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
