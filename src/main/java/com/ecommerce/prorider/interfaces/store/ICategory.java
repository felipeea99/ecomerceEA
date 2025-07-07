package com.ecommerce.prorider.interfaces.store;

import com.ecommerce.prorider.DTOs.request.store.CategoryRequest;
import com.ecommerce.prorider.DTOs.response.store.CategoryResponse;
import com.ecommerce.prorider.DTOs.update.store.CategoryUpdate;
import com.ecommerce.prorider.entities.store.Category;

import java.util.List;

public interface ICategory {
    List<CategoryResponse> findAllCategories();
    CategoryResponse findCategoryByID(int categoryID);
    Boolean deleteCategory(int categoryID);
    CategoryResponse addCategory(CategoryRequest categoryRequest );
    CategoryResponse editCategory(CategoryUpdate categoryUpdate);
    Category findCategoryByIdBaseForm(int categoryID);
    CategoryResponse ToCategoryResponse(Category category);
}
