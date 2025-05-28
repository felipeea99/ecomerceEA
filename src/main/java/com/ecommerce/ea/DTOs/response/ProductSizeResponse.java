package com.ecommerce.ea.DTOs.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductSizeResponse {
    private int productSizeId;
    private double price;
    private boolean isActive;
    private int sizeId;
    private int productMultiPriceId;
}
