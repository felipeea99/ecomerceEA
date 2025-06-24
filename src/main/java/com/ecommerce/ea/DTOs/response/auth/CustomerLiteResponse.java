package com.ecommerce.ea.DTOs.response.auth;

import com.ecommerce.ea.DTOs.response.store.StoreLiteResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerLiteResponse {
    private UUID customerId;
    private UserResponse user;
}
