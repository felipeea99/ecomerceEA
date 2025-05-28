package com.ecommerce.ea.DTOs.update;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductMultiPriceUpdate extends ProductUpdate {
    @NotNull
    private Boolean multiPrice;
}
