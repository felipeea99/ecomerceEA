package com.ecommerce.ea.repository;

import com.ecommerce.ea.entities.Product;
import com.ecommerce.ea.entities.ProductSinglePrice;
import com.ecommerce.ea.entities.Store;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductSingleRepository extends ReactiveCrudRepository<ProductSinglePrice,Integer> {
    Mono<List<ProductSinglePrice>> GetAllProductsByStoreId(UUID storeId);
}
