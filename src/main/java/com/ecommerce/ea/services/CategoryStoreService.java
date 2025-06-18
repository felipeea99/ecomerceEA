package com.ecommerce.ea.services;

import com.ecommerce.ea.DTOs.request.CategoryStoreRequest;
import com.ecommerce.ea.DTOs.response.CategoryResponse;
import com.ecommerce.ea.entities.Category;
import com.ecommerce.ea.entities.CategoryStore;
import com.ecommerce.ea.entities.Store;
import com.ecommerce.ea.interfaces.ICategoryStore;
import com.ecommerce.ea.repository.CategoryStoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class CategoryStoreService implements ICategoryStore {

    private final CategoryStoreRepository categoryStoreRepository;
    private final CategoryService categoryService;
    private  final StoreService storeService;

    public CategoryStoreService(CategoryStoreRepository categoryStoreRepository, CategoryService categoryService, StoreService storeService){
        this.categoryStoreRepository = categoryStoreRepository;
        this.categoryService = categoryService;
        this.storeService = storeService;
    }

    @Override
    public List<CategoryResponse> findCategoriesByStoreId(UUID storeId) {
        // Retrieve all the categories that have the store
        List<CategoryStore> cs = categoryStoreRepository.findAllCategoriesByStoreId(storeId);

        // Show the list of categories available for that store
        return cs.stream()
                        .map(CategoryStore::getCategory)
                        .map(CategoryResponse::ToCategoryResponseObj)
                        .toList();
    }


    @Override
    public Boolean deleteCategoryByID(int categoryStoreId) {
        //categoryStoreId validation
        categoryService.findCategoryByIdBaseForm(categoryStoreId);
        //remove the item from the database
        categoryService.deleteCategory(categoryStoreId);

        return true;
    }

    @Override
    public CategoryResponse addCategoryStore(CategoryStoreRequest categoryStoreRequest) {
        //Transform the request into entity
        CategoryStore categoryStore =this.ToCategoryStore(categoryStoreRequest);
        //Save it on the database
        CategoryStore categorySaved = categoryStoreRepository.save(categoryStore);
        //Transform it into CategoryResponse
        return CategoryResponse.ToCategoryResponseObj(categorySaved.getCategory());
    }

    @Override
    public CategoryStore ToCategoryStore(CategoryStoreRequest request) {
        /// Category Initialization
        Category category = categoryService.findCategoryByIdBaseForm(request.getCategoryId());
        /// Store Initialization
        Store store  = storeService.findStoreByIdBaseForm(request.getStoreId());
        /// CategoryStore Initialization
        CategoryStore categoryStore = new CategoryStore();
        categoryStore.setCategory(category);
        categoryStore.setStore(store);

        return categoryStore;
    }
}
