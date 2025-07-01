package com.ecommerce.ea.DTOs.request.auth;

import com.ecommerce.ea.entities.auth.UserAcc;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateStoreRequest {
    private UserAccRequest userAcc;
    private StoreRequest storeRequest;
}
