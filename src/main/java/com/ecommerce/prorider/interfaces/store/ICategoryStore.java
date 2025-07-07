package com.ecommerce.prorider.interfaces.store;

import com.ecommerce.prorider.DTOs.request.store.CategoryStoreRequest;
import com.ecommerce.prorider.DTOs.response.store.CategoryResponse;
import com.ecommerce.prorider.entities.store.CategoryStore;

import java.util.List;
import java.util.UUID;

public interface ICategoryStore {
    List<CategoryResponse> findCategoriesByStoreId(UUID storeId);
    Boolean deleteCategoryByID(int categoryStoreId);
    CategoryResponse addCategoryStore(CategoryStoreRequest categoryStoreRequest );
    CategoryStore ToCategoryStore(CategoryStoreRequest request);

    }
