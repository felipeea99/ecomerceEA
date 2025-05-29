package com.ecommerce.ea.repository;

import com.ecommerce.ea.DTOs.response.PhotoResponse;
import com.ecommerce.ea.entities.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface PhotoRepository extends JpaRepository<Photo, Number> {
    @Async
    CompletableFuture<List<Photo>> findAllPhotosByProductId(int productId);
    List<Photo> findByProductIDOrderByPhotosID(int productID);
    CompletableFuture<List<Photo>> findByIndex(int index);

}
