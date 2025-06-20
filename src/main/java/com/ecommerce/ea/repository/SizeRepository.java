package com.ecommerce.ea.repository;

import com.ecommerce.ea.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
@Repository
public interface SizeRepository extends JpaRepository<Size, Integer> {

}
