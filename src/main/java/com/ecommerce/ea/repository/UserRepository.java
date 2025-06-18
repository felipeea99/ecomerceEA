package com.ecommerce.ea.repository;

import com.ecommerce.ea.entities.Customer;
import com.ecommerce.ea.entities.UserAcc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Repository
public interface UserRepository extends JpaRepository<UserAcc, UUID> {

}
