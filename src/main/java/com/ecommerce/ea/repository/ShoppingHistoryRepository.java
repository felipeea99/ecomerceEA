package com.ecommerce.ea.repository;

import com.ecommerce.ea.DTOs.response.ShoppingHistoryResponse;
import com.ecommerce.ea.entities.ShoppingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface ShoppingHistoryRepository extends ReactiveCrudRepository<ShoppingHistory, Integer> {
    @Async
    CompletableFuture<List<ShoppingHistory>> findAllItemsBoughtByUserId (UUID userId);
}
