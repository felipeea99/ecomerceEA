package com.ecommerce.ea.repository.store;

import com.ecommerce.ea.entities.store.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    @Query("SELECT p FROM Product p WHERE p.store = :store")
    List<Product> FindAllProductsByStoreId(@Param("store") UUID store);
}
