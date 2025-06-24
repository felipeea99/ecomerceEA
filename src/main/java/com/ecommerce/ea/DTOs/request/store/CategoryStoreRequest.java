package com.ecommerce.ea.DTOs.request.store;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryStoreRequest {
    @NotNull
    private int categoryId;
    @NotNull
    private UUID storeId;

}
