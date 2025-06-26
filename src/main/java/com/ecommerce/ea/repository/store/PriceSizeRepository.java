package com.ecommerce.ea.repository.store;

import com.ecommerce.ea.DTOs.response.store.PriceBySizeResponse;
import com.ecommerce.ea.entities.store.PriceBySize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PriceSizeRepository extends JpaRepository<PriceBySize, Integer> {
    @Query("SELECT p FROM PriceBySize p WHERE p.product = :product")
    List<PriceBySize> findAllByProductId(@Param("product") int productId);
    @Query("SELECT p FROM PriceBySize p WHERE p.product.storeId = :storeId")
    List<PriceBySize> findAllPriceBySizeByStoreId(@Param("storeId") UUID storeId);
}
