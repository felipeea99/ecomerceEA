package com.ecommerce.ea.DTOs.request;

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
public class ProductSizeRequest {
    @Min(0)
    private double price;
    private boolean isActive;

    @NotNull
    private int sizeId;  // solo ID para referencia

    @NotNull
    private int productMultiPriceId;  // solo ID para referencia
}
