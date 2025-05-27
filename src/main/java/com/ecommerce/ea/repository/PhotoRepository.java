package com.ecommerce.ea.repository;

import com.ecommerce.ea.entities.Photo;
import com.ecommerce.ea.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface PhotoRepository extends JpaRepository<Photo, Number> {
    @Async
    public CompletableFuture<List<Product>> findAllPhotosByProductId(int productId);
}
