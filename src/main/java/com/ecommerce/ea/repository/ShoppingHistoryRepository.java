package com.ecommerce.ea.repository;

import com.ecommerce.ea.entities.ShoppingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Repository
public interface ShoppingHistoryRepository extends JpaRepository<ShoppingHistory, Integer> {
    @Query("SELECT s FROM ShoppingHistory s WHERE s.customer = :customer")
    CompletableFuture<List<ShoppingHistory>> findAllItemsBoughtByCustomerId(@Param("customer") UUID customer);
    @Query("SELECT s FROM ShoppingHistory s WHERE s.store = :store")
    CompletableFuture<List<ShoppingHistory>> findAllItemsByStoreId(@Param("store")UUID store);
}
