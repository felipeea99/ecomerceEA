package com.ecommerce.ea.DTOs.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductMultiPriceRequest extends ProductRequest {
    @NotNull
    private Boolean multiPrice;
}
