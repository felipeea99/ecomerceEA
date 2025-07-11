package com.ecommerce.ea.DTOs.update.payments;

import com.ecommerce.ea.entities.payments.SubscriptionDb;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SubscriptionHistoryUpdate {
    private int subscriptionHistoryId;
    @NotNull
    private SubscriptionDb subscription;
}
