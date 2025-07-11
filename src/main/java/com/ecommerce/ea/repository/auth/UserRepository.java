package com.ecommerce.ea.repository.auth;

import com.ecommerce.ea.entities.auth.UserAcc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserAcc, UUID> {

}
