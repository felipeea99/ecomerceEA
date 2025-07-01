package com.ecommerce.ea.entities.store;


import com.ecommerce.ea.entities.auth.Store;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;
    @NotBlank
    private String productName;

    private boolean isActive;
    private boolean hasSizes;
    private BigDecimal price;
    private String description;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "storeId")
    private Store store;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PriceBySize> pricesBySize = new ArrayList<>();
}
