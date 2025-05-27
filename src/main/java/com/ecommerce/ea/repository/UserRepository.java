package com.ecommerce.ea.repository;

import com.ecommerce.ea.entities.UserAcc;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserAcc, UUID> {
}
