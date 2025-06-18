package com.ecommerce.ea.repository;

import com.ecommerce.ea.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    @Query("SELECT a FROM Address a WHERE a.customer = :customer")
    List<Address> findAllAddressesByCustomerId(@Param("customer") UUID customer);
}
