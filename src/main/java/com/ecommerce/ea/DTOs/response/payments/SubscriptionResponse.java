package com.ecommerce.ea.DTOs.response.payments;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionResponse {
    private String status;
    private LocalDate startDate;
    private LocalDate endDate;
    private String storeName;
}
