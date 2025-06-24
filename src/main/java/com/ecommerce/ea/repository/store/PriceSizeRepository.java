package com.ecommerce.ea.repository.store;

import com.ecommerce.ea.entities.store.PriceBySize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceSizeRepository extends JpaRepository<PriceBySize, Integer> {
    @Query("SELECT p FROM PriceBySize p WHERE p.product = :product")
    List<PriceBySize> findAllByProductId(@Param("product") int productId);

}
