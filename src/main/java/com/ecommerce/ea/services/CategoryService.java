package com.ecommerce.ea.services;

import com.ecommerce.ea.DTOs.request.CategoryRequest;
import com.ecommerce.ea.DTOs.response.CategoryResponse;
import com.ecommerce.ea.DTOs.update.CategoryUpdate;
import com.ecommerce.ea.entities.Category;
import com.ecommerce.ea.exceptions.BadRequestException;
import com.ecommerce.ea.interfaces.ICategory;
import com.ecommerce.ea.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class CategoryService implements ICategory {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CompletableFuture<List<CategoryResponse>> GetAllCategories() {
        //Retrieve all the categories objects from the database
        CompletableFuture<List<Category>> categoryList = categoryRepository.findAllAsync();
        //Convert each element into CompletableFuture<List<CategoryResponse>>
        return categoryList.thenApply(category -> category.stream().map(CategoryResponse::ToCategoryResponseObj).collect(Collectors.toList()) ) ;
    }

    @Override
    public CompletableFuture<CategoryResponse> GetCategoryByID(int categoryID) {
        //categoryId validation
        Category category = categoryRepository.findById(categoryID)
                .orElseThrow(() -> new BadRequestException("CategoryId not found on the database" + categoryID));
        //Convert the Category object into CategoryResponse obj
        CategoryResponse categoryResponse = CategoryResponse.ToCategoryResponseObj(category);
        return CompletableFuture.completedFuture(categoryResponse);
    }

    @Override
    public CompletableFuture<Boolean> DeleteCategoryByID(int categoryID) {
        try {
            categoryRepository.deleteById(categoryID);
            return CompletableFuture.completedFuture(true);
        } catch (Exception e) {
            return CompletableFuture.completedFuture(false);
        }
    }

    @Transactional
    @Override
    public CompletableFuture<CategoryResponse> AddCategory(CategoryRequest categoryRequest) {
        //Convert the categoryRequest into Category Object
        Category category = categoryRequest.ToCategoryObj();
        //Stores it and saved it into the variable
       Category categorySaved =  this.categoryRepository.save(category);
       //Convert the savedObject into CategoryResponse
        CategoryResponse categoryResponse =  CategoryResponse.ToCategoryResponseObj(categorySaved);
        return CompletableFuture.completedFuture(categoryResponse);
    }

    @Override
    public CompletableFuture<CategoryResponse> EditCategory(CategoryUpdate categoryUpdate) {
        // find the CategoryObj to Edit on the database base on the categoryId
        int categoryId = categoryUpdate.getCategoryId();
        Category categoryObj = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new BadRequestException("CategoryId not found on the database" + categoryId));

        //Edit the changes and save them and stores it into the categorySaved variable
        categoryObj.setCategoryName(categoryObj.getCategoryName());
        Category categorySaved =categoryRepository.save(categoryObj);
        //Convert categorySaved into CategoryResponse object
        CategoryResponse categoryResponse = CategoryResponse.ToCategoryResponseObj(categorySaved);
        return CompletableFuture.completedFuture(categoryResponse);
    }
}
