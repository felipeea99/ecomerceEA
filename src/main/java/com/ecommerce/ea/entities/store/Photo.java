package com.ecommerce.ea.entities.store;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int photoID;
    @NotBlank
    private String photoValue;
    private int index;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;


}
