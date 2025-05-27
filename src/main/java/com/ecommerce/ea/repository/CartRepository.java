package com.ecommerce.ea.repository;

import com.ecommerce.ea.entities.Address;
import com.ecommerce.ea.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface CartRepository extends JpaRepository<Cart, Number> {
    @Async
    CompletableFuture<List<Cart>> findAllCartsByUserId(UUID userId);
}
