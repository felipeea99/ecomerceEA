package com.ecommerce.ea.DTOs.request.payments;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionRequest {
    private UUID storeId;
    private String stripeCustomerId;
    private String stripePriceId;
}
