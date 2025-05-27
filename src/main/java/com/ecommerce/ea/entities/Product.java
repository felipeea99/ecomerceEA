package com.ecommerce.ea.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;
    @NotBlank
    private String productName;
    private boolean isActive;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "storeId")
    private Store store;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;
}
