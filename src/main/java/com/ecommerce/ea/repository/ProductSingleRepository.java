package com.ecommerce.ea.repository;

import com.ecommerce.ea.entities.Product;
import com.ecommerce.ea.entities.ProductSinglePrice;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSingleRepository extends ReactiveCrudRepository<ProductSinglePrice,Integer> {
}
