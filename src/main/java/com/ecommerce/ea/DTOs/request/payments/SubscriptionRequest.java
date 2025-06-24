package com.ecommerce.ea.DTOs.request.payments;

import com.ecommerce.ea.DTOs.response.payments.SubscriptionResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
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
