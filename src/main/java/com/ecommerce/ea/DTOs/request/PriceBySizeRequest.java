package com.ecommerce.ea.DTOs.request;

import com.ecommerce.ea.entities.PriceBySize;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private double price;
}
