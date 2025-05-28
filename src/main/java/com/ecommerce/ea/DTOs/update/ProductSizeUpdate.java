package com.ecommerce.ea.DTOs.update;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductSizeUpdate {
    private int productSizeId;

    @Min(0)
    private double price;
    private boolean isActive;

    @NotNull
    private int sizeId;

    @NotNull
    private int productMultiPriceId;
}
