package com.ecommerce.prorider.repository.auth;

import com.ecommerce.prorider.DTOs.response.auth.UserResponse;
import com.ecommerce.prorider.entities.auth.UserAcc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserAcc, UUID> {
    @Query("SELECT u FROM UserAcc u WHERE u.username = :username")
    UserAcc findByUserName(@Param("username") String username);
    UserAcc findByUserId(UUID userId);
}
