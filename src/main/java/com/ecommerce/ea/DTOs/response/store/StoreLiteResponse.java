package com.ecommerce.ea.DTOs.response.store;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * This Class it is used to share certain store details for ProductResponse
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StoreLiteResponse {
    private UUID storeId;
    private String storeName;
}
