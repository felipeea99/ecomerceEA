package com.ecommerce.ea.interfaces;

import com.ecommerce.ea.entities.UserAcc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IUserAcc extends JpaRepository<UserAcc, UUID>{

UserAcc findByUserName(String username);
}
