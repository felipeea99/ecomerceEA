package com.ecommerce.ea.interfaces;

import com.ecommerce.ea.DTOs.request.CategoryRequest;
import com.ecommerce.ea.DTOs.response.CategoryResponse;
import com.ecommerce.ea.DTOs.update.CategoryUpdate;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ICategory {
    CompletableFuture<List<CategoryResponse>> GetAllCategories();
    CompletableFuture<CategoryResponse> GetCategoryByID(int categoryID);
    CompletableFuture<Boolean> DeleteCategoryByID(int categoryID);
    CompletableFuture<CategoryResponse> AddCategory(CategoryRequest categoryRequest );
    CompletableFuture<CategoryResponse> EditCategory(CategoryUpdate categoryUpdate);
}
