package com.ecommerce.ea.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ProductSize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productSizeId;
    @NotBlank
    @Min(0)
    private double price;
    private boolean isActive;
    @NotNull
    @NotNull
    @ManyToOne
    @JoinColumn(name = "sizeId")
    private Size size;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "productId")
    private ProductMultiPrice productMultiPrice;
}
