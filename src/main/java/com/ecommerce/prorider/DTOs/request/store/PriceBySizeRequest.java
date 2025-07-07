package com.ecommerce.prorider.DTOs.request.store;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PriceBySizeRequest {
    private int productId;
    @ManyToOne
    @JoinColumn(name = "sizeId", nullable = false)
    private int sizeId;
    @Min(0)
    private BigDecimal price;
}
