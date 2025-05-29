package com.ecommerce.ea.DTOs.request;

import com.ecommerce.ea.entities.Category;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
public class CategoryRequest {
    @Min(1)
    private int categoryId;
    @NotBlank(message = "category name should not be empty")
    private String categoryName;

    public Category ToCategoryObj(){
        Category category = new Category();
        category.setCategoryName(this.categoryName);
        return category;
    }
}
