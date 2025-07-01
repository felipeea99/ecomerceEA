package com.ecommerce.ea.interfaces.store;

import com.ecommerce.ea.DTOs.request.store.CategoryRequest;
import com.ecommerce.ea.DTOs.response.store.CategoryResponse;
import com.ecommerce.ea.DTOs.update.store.CategoryUpdate;
import com.ecommerce.ea.entities.store.Category;

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
