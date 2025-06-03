package com.ecommerce.ea.repository;

import com.ecommerce.ea.DTOs.response.PhotoResponse;
import com.ecommerce.ea.entities.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.scheduling.annotation.Async;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface PhotoRepository extends ReactiveCrudRepository<Photo, Number> {
    @Async
    Mono<List<Photo>> findAllPhotosByProductId(int productId);
    Mono<List<Photo>> findByProductIDOrderByPhotosID(int productID);
    Mono<List<Photo>> findByIndex(int index);

}
