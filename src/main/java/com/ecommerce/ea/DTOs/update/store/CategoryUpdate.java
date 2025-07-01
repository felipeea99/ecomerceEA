package com.ecommerce.ea.DTOs.update.store;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryUpdate {
    @Min(1)
    private int categoryId;
    @NotBlank(message = "category name should not be empty")
    private String categoryName;
}
