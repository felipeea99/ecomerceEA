package com.ecommerce.ea.repository;

import com.ecommerce.ea.entities.ProductMultiPrice;
import com.ecommerce.ea.entities.ProductSinglePrice;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductMultiRepository extends ReactiveCrudRepository<ProductMultiPrice,Integer> {
}
