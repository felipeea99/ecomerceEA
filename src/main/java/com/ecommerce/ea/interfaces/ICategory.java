package com.ecommerce.ea.interfaces;

import com.ecommerce.ea.DTOs.request.CategoryRequest;
import com.ecommerce.ea.DTOs.response.CategoryResponse;
import com.ecommerce.ea.DTOs.update.CategoryUpdate;
import com.ecommerce.ea.entities.Category;

import java.util.List;

public interface ICategory {
    List<CategoryResponse> findAllCategories();
    CategoryResponse findCategoryByID(int categoryID);
    Boolean deleteCategory(int categoryID);
    CategoryResponse addCategory(CategoryRequest categoryRequest );
    CategoryResponse editCategory(CategoryUpdate categoryUpdate);
    Category findCategoryByIdBaseForm(int categoryID);

}
