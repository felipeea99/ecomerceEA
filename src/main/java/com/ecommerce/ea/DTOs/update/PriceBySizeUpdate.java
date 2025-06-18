package com.ecommerce.ea.DTOs.update;

import com.ecommerce.ea.entities.Size;
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
public class PriceBySizeUpdate {
    private int priceBySizeId;
    @ManyToOne
    @JoinColumn(name = "sizeId", nullable = false)
    private Size sizeName;
    @Min(0)
    private double price;
    private int productId;
}
