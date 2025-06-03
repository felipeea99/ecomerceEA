package com.ecommerce.ea.repository;

import com.ecommerce.ea.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Repository
public interface CategoryRepository extends ReactiveCrudRepository<Category, Integer> {
    @Async
    Mono<List<Category>> findAllAsync();

}
