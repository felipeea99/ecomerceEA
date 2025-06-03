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
import reactor.core.publisher.Mono;

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
    public Mono<List<CategoryResponse>> GetAllCategories() {
        //Retrieve all the categories objects from the database
        Mono<List<Category>> categoryList = categoryRepository.findAllAsync();
        //Convert each element into CompletableFuture<List<CategoryResponse>>
        return categoryList.map(category -> category.stream().map(CategoryResponse::ToCategoryResponseObj).collect(Collectors.toList()) ) ;
    }

    @Override
    public Mono<CategoryResponse> GetCategoryByID(int categoryID) {
        //categoryId validation
       Mono<Category> category = categoryRepository.findById(categoryID)
                .switchIfEmpty(Mono.error(new BadRequestException("CategoryId not found on the database" + categoryID)));
        //Convert the Category object into CategoryResponse obj
        return category.map(CategoryResponse::ToCategoryResponseObj);
    }

    @Override
    public Mono<Boolean> DeleteCategoryByID(int categoryID) {
          return categoryRepository.deleteById(categoryID).thenReturn(true).onErrorReturn(false);
    }

    @Transactional
    @Override
    public Mono<CategoryResponse> AddCategory(CategoryRequest categoryRequest) {
        //Convert the categoryRequest into Category Object
        Category category = categoryRequest.ToCategoryObj();
        //Stores it and saved it into the variable
        Mono <Category> categorySaved =  this.categoryRepository.save(category);
       //Convert the savedObject into CategoryResponse
        return categorySaved.map(CategoryResponse::ToCategoryResponseObj);
    }

    @Override
    public Mono<CategoryResponse> EditCategory(CategoryUpdate categoryUpdate) {
        // find the CategoryObj to Edit on the database base on the categoryId
        int categoryId = categoryUpdate.getCategoryId();
      return categoryRepository.findById(categoryId)
                .switchIfEmpty(Mono.error(new BadRequestException("CategoryId not found on the database")))
                .flatMap(category -> {
                    //Edit the changes and save them and stores it into the categorySaved variable
                    category.setCategoryName(categoryUpdate.getCategoryName());
                    //Convert categorySaved into CategoryResponse object
                    return categoryRepository.save(category).map(CategoryResponse::ToCategoryResponseObj);
                });
    }
}
