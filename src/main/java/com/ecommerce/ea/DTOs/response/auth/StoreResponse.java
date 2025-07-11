package com.ecommerce.ea.DTOs.response.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StoreResponse {
    private UUID storeID;
    private String storeName;
    private String storeEmail;
    private String domain;
    private UUID userId;
    private boolean onboardingCompleted;

}
