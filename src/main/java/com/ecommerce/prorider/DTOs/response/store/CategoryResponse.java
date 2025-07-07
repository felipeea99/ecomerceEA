package com.ecommerce.prorider.DTOs.response.store;

import com.ecommerce.prorider.entities.store.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {
    private int categoryId;
    private String categoryName;

    public static CategoryResponse ToCategoryResponseObj(Category category){
        return new CategoryResponse(
                category.getCategoryId(),
                category.getCategoryName()
        );
    }
}

