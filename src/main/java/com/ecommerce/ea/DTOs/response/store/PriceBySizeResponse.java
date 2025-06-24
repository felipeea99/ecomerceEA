package com.ecommerce.ea.DTOs.response.store;

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
    private int productId;
    private int sizeId;
    private BigDecimal price;

}
