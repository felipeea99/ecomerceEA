package com.ecommerce.ea.repository;

import com.ecommerce.ea.entities.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface StoreRepository extends ReactiveCrudRepository<Store, UUID> {

}
