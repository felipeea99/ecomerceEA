package com.ecommerce.ea.DTOs.response.store;

import com.ecommerce.ea.entities.store.Category;
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

