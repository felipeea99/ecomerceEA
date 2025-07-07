package com.ecommerce.prorider.controllers.store;

import com.ecommerce.prorider.DTOs.request.store.CategoryRequest;
import com.ecommerce.prorider.DTOs.response.store.CategoryResponse;
import com.ecommerce.prorider.DTOs.update.store.CategoryUpdate;
import com.ecommerce.prorider.services.store.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@PreAuthorize("hasRole('ADMIN')")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public CategoryResponse addCategory(@Valid @RequestBody CategoryRequest categoryRequest){
        return this.categoryService.addCategory(categoryRequest);
    }

    @GetMapping("/{categoryId}")
    public CategoryResponse findCategoryByID(int categoryId){
        return this.categoryService.findCategoryByID(categoryId);
    }

    @GetMapping
    public List<CategoryResponse> findAllCategories(){
        return this.categoryService.findAllCategories();
    }

    @PutMapping
    public CategoryResponse editCategory(@Valid @RequestBody CategoryUpdate categoryUpdate){
        return this.categoryService.editCategory(categoryUpdate);
    }

    @DeleteMapping("/{categoryId}")
    public Boolean deleteCategory(@PathVariable int categoryId){
        return this.categoryService.deleteCategory(categoryId);
    }

}
