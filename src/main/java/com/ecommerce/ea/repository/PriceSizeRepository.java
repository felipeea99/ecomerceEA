package com.ecommerce.ea.repository;

import com.ecommerce.ea.entities.PriceBySize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Repository
public interface PriceSizeRepository extends JpaRepository<PriceBySize, Integer> {
    @Query("SELECT p FROM PriceBySize p WHERE p.product = :product")
    List<PriceBySize> findAllByProductId(@Param("product") int productId);

}
