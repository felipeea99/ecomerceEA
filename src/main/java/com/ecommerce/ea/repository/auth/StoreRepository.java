package com.ecommerce.ea.repository.auth;

import com.ecommerce.ea.entities.auth.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StoreRepository extends JpaRepository<Store, UUID> {
    @Query("SELECT s FROM Store s WHERE s.storeName = :storeName ")
    Store findStoreByStoreNameDb(@Param("storeName") String storeName);
}
