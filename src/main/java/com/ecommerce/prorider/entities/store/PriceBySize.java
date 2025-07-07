package com.ecommerce.prorider.entities.store;

import jakarta.persistence.*;
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
@Entity
public class PriceBySize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int priceBySizeId;

    @ManyToOne
    @JoinColumn(name = "sizeId", nullable = false)
    private Size size;

    @ManyToOne
    @JoinColumn(name = "productId", nullable = false)
    private Product product;

    @Min(0)
    private BigDecimal price;
}
