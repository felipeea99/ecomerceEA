package com.ecommerce.ea.repository;

import com.ecommerce.ea.entities.Address;
import com.ecommerce.ea.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface AddressRepository extends JpaRepository<Address, Number> {
    @Async
    CompletableFuture<List<Address>> findAllAddressesByUserIdAsync(UUID userId);
}
