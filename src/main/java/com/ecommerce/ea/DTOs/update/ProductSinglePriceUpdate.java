package com.ecommerce.ea.DTOs.update;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductSinglePriceUpdate extends ProductUpdate {
    @Min(0)
    private double price;
}
