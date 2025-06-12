package com.ecommerce.ea.controllers;

import com.ecommerce.ea.DTOs.request.CategoryRequest;
import com.ecommerce.ea.DTOs.response.CategoryResponse;
import com.ecommerce.ea.DTOs.update.CategoryUpdate;
import com.ecommerce.ea.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/// This Controller is for the Admin only

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public CompletableFuture<CategoryResponse> addCategory(@Valid @RequestBody CategoryRequest categoryRequest){
        return this.categoryService.addCategory(categoryRequest);
    }

    @GetMapping("/{categoryId}")
    public CompletableFuture<CategoryResponse> findCategoryByID(int categoryId){
        return this.categoryService.findCategoryByID(categoryId);
    }

    @GetMapping
    public CompletableFuture<List<CategoryResponse>> findCategoryByID(){
        return this.categoryService.findAllCategories();
    }

    @PutMapping
    public CompletableFuture<CategoryResponse> editCategory(@Valid @RequestBody CategoryUpdate categoryUpdate){
        return this.categoryService.editCategory(categoryUpdate);
    }

    @DeleteMapping("/{categoryId}")
    public CompletableFuture<Boolean>deleteCategory(@RequestParam int categoryId){
        return this.categoryService.deleteCategoryByID(categoryId);
    }

}
