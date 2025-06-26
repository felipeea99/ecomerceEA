package com.ecommerce.ea.DTOs.response.payments;

import com.ecommerce.ea.entities.payments.SubscriptionDb;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SubscriptionHistoryResponse {
    private int subscriptionHistoryId;
    private SubscriptionDbResponse subscription;
}
