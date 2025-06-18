package com.ecommerce.ea.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CategoryStore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryStoreId;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "storeId")
    private Store store;
}
