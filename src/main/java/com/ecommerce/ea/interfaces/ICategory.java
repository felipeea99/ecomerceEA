package com.ecommerce.ea.interfaces;

import com.ecommerce.ea.entities.Category;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ICategory {
    CompletableFuture<List<Category>> GetAllCategories();
    CompletableFuture<Category> GetCategoryByID(int categoryID);
    CompletableFuture<Boolean> DeleteCategoryByID(int categoryID);
    CompletableFuture<Category> AddCategory(Category category );
    CompletableFuture<Category> EditCategory(Category category);
}
