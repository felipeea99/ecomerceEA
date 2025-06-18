package com.ecommerce.ea.DTOs.response;

import com.ecommerce.ea.entities.PriceBySize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PriceBySizeResponse {

    private int priceBySizeId;
    private int productId;
    private int sizeId;
    private double price;

}
