package com.ecommerce.ea.repository.auth;

import com.ecommerce.ea.entities.auth.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, UUID> {
    @Query("SELECT vt FROM VerificationToken vt WHERE vt.token = :token")
    Optional<VerificationToken> findByToken(@Param("token") String token);
}
