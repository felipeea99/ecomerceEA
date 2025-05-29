package com.ecommerce.ea.repository;

import com.ecommerce.ea.entities.ShoppingHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingHistoryRepository extends JpaRepository<ShoppingHistory, Integer> {
}
