package com.ecommerce.ea.DTOs.response.payments;

import com.ecommerce.ea.DTOs.response.store.StoreLiteResponse;
import com.ecommerce.ea.entities.auth.Store;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionDbResponse {
    private String status;
    private LocalDate startDate;
    private LocalDate endDate;
    private String storeName;
    private String stripePriceId;
    private String stripeCustomerId;
}
