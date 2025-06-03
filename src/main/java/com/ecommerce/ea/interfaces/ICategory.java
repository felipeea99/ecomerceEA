package com.ecommerce.ea.interfaces;

import com.ecommerce.ea.DTOs.request.CategoryRequest;
import com.ecommerce.ea.DTOs.response.CategoryResponse;
import com.ecommerce.ea.DTOs.update.CategoryUpdate;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ICategory {
    Mono<List<CategoryResponse>> GetAllCategories();
    Mono<CategoryResponse> GetCategoryByID(int categoryID);
    Mono<Boolean> DeleteCategoryByID(int categoryID);
    Mono<CategoryResponse> AddCategory(CategoryRequest categoryRequest );
    Mono<CategoryResponse> EditCategory(CategoryUpdate categoryUpdate);
}
