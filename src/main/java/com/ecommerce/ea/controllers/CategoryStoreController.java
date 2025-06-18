package com.ecommerce.ea.controllers;

import com.ecommerce.ea.AOP_Functions.annotations.ValidateStoreAccess;
import com.ecommerce.ea.AOP_Functions.context.StoreContextHolder;
import com.ecommerce.ea.DTOs.request.CategoryStoreRequest;
import com.ecommerce.ea.DTOs.response.CategoryResponse;
import com.ecommerce.ea.services.CategoryStoreService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/categoryStore")
public class CategoryStoreController {
    /// Services
    private final CategoryStoreService categoryStoreService;
    /// Constructor
    public CategoryStoreController(CategoryStoreService categoryStoreService) {
        this.categoryStoreService = categoryStoreService;
    }
    /// EndPoints

    /// This EndPoint find a list of categoryStore objects base on the storeId that is retrieved from the ValidateStoreAccess using the storeName
    @GetMapping("/{storeName}")
    @ValidateStoreAccess
    public List<CategoryResponse> findCategoriesByStoreId(){
        /// Gets the StoreContextHolder.storeId();
        UUID storeId = StoreContextHolder.getStoreId();
        return this.categoryStoreService.findCategoriesByStoreId(storeId);
    }

    /// This EndPoint is used to delete a categoryStore object, it receives the storeName and the categoryStoreId
    @ValidateStoreAccess
    @DeleteMapping("/{storeName}/{categoryStoreId}")
    public Boolean deleteAddress(@PathVariable int categoryStoreId){
        return this.categoryStoreService.deleteCategoryByID(categoryStoreId);
    }

    /// This EndPoint search for a List of Address objects, using the ValidateStoreAccess to retrieve the customerId
    @ValidateStoreAccess
    @GetMapping("/{storeName}")
    public CategoryResponse addCategoryStore(@RequestBody CategoryStoreRequest request){
        /// Gets the StoreContextHolder.storeId();
        UUID storeId = StoreContextHolder.getStoreId();
        /// Set the StoreId of the request to the one obtained from the name
        request.setStoreId(storeId);
        return categoryStoreService.addCategoryStore(request);
    }
}

