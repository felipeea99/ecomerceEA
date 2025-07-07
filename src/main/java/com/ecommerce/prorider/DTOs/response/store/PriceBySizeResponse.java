package com.ecommerce.prorider.DTOs.response.store;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PriceBySizeResponse {

    private int priceBySizeId;
    private ProductResponse product;
    private SizeResponse size;
    private BigDecimal price;
    private int quantity;
}
