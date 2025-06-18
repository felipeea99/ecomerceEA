package com.ecommerce.ea.interfaces;

import com.ecommerce.ea.DTOs.request.CategoryStoreRequest;
import com.ecommerce.ea.DTOs.response.CategoryResponse;
import com.ecommerce.ea.entities.CategoryStore;

import java.util.List;
import java.util.UUID;

public interface ICategoryStore {
    List<CategoryResponse> findCategoriesByStoreId(UUID storeId);
    Boolean deleteCategoryByID(int categoryStoreId);
    CategoryResponse addCategoryStore(CategoryStoreRequest categoryStoreRequest );
    CategoryStore ToCategoryStore(CategoryStoreRequest request);

    }
