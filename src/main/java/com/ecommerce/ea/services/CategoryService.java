package com.ecommerce.ea.services;

import com.ecommerce.ea.entities.Category;
import com.ecommerce.ea.exceptions.BadRequestException;
import com.ecommerce.ea.interfaces.ICategory;
import com.ecommerce.ea.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class CategoryService implements ICategory {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CompletableFuture<List<Category>> GetAllCategories() {
        return categoryRepository.findAllAsync();
    }

    @Override
    public CompletableFuture<Category> GetCategoryByID(int categoryID) {
        Category category = categoryRepository.findById(categoryID)
                .orElseThrow(() -> new BadRequestException("CategoryId not found" + categoryID));
        return CompletableFuture.completedFuture(category);
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
    public CompletableFuture<Category> AddCategory( Category category) {
       Category categoryObj =  this.categoryRepository.save(category);
        return CompletableFuture.completedFuture(categoryObj);
    }

    @Override
    public CompletableFuture<Category> EditCategory(Category category) {
        int categoryId = category.getCategoryId();

        Category categoryObj = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new BadRequestException("CategoryId not found" + categoryId));

        //Edit Section
        categoryObj.setCategoryName(category.getCategoryName());

        categoryRepository.save(categoryObj);

        return CompletableFuture.completedFuture(categoryObj);
    }
}
