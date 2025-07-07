package com.ecommerce.prorider.repository.auth;

import com.ecommerce.prorider.entities.auth.Store;
import com.ecommerce.prorider.entities.auth.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, UUID> {
    @Query("SELECT vt FROM VerificationToken vt WHERE vt.token = :token")
    Optional<VerificationToken> findByToken(@Param("token") String token);
    @Query("SELECT vt FROM VerificationToken vt WHERE vt.store = :store")
    Optional<VerificationToken>findByStore(@Param("store") Store store);

}
