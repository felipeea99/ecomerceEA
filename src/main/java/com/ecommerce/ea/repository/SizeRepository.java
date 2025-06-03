package com.ecommerce.ea.repository;

import com.ecommerce.ea.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface SizeRepository extends ReactiveCrudRepository<Size, Integer> {
}
