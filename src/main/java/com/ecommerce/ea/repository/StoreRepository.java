package com.ecommerce.ea.repository;

import com.ecommerce.ea.entities.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Repository
public interface StoreRepository extends JpaRepository<Store, UUID> {
    @Query("SELECT s FROM Store s WHERE s.storeName = :storeName ")
    Store findStoreByStoreNameDb(@Param("storeName") String storeName);
}
