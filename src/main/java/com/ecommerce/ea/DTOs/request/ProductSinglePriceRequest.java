package com.ecommerce.ea.DTOs.request;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductSinglePriceRequest extends ProductRequest {
    @Min(0)
    private double price;
}
