package com.ecommerce.ea.repository;

import com.ecommerce.ea.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Async
    CompletableFuture<List<Category>> findAllAsync();

}
