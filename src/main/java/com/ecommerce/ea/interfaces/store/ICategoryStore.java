package com.ecommerce.ea.interfaces.store;

import com.ecommerce.ea.DTOs.request.store.CategoryStoreRequest;
import com.ecommerce.ea.DTOs.response.store.CategoryResponse;
import com.ecommerce.ea.entities.store.CategoryStore;

import java.util.List;
import java.util.UUID;

public interface ICategoryStore {
    List<CategoryResponse> findCategoriesByStoreId(UUID storeId);
    Boolean deleteCategoryByID(int categoryStoreId);
    CategoryResponse addCategoryStore(CategoryStoreRequest categoryStoreRequest );
    CategoryStore ToCategoryStore(CategoryStoreRequest request);

    }
