package com.ecommerce.prorider.repository.store;

import com.ecommerce.prorider.entities.store.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    @Query("SELECT c FROM Cart c WHERE c.customer = :customer")
    List<Cart> findAllCartsByCustomerId(@Param("customer")UUID customer);
}
