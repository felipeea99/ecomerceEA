package com.ecommerce.ea.repository;

import com.ecommerce.ea.entities.CategoryStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Repository
public interface CategoryStoreRepository extends JpaRepository<CategoryStore, Integer> {
    @Query("SELECT cs FROM CategoryStore cs WHERE cs.store = :store")
    List<CategoryStore> findAllCategoriesByStoreId(@Param("store") UUID store);
}
