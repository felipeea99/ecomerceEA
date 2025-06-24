package com.ecommerce.ea.DTOs.request.store;

import com.ecommerce.ea.entities.store.Category;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest {
    @NotBlank(message = "category name should not be empty")
    private String categoryName;

    public Category ToCategoryObj(){
        Category category = new Category();
        category.setCategoryName(this.categoryName);
        return category;
    }
}
