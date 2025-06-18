package com.ecommerce.ea.repository;

import com.ecommerce.ea.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    @Query("SELECT c FROM Customer c WHERE c.user = :user")
    List<Customer> findAllCustomerByUser(@Param("user")UUID user);
    @Query("SELECT c FROM Customer c WHERE c.user = :user AND c.storeName = :storeName")
    Optional<Customer> findCustomerByUser(@Param("user")UUID user , @Param("store") String storeName);
}
