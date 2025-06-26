package com.ecommerce.ea.repository.payments;

import com.ecommerce.ea.entities.payments.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order,UUID > {
    @Query("SELECT o FROM Order o WHERE o.stripePaymentIntentId = :stripePaymentIntentId")
    Optional<Order> findByStripePaymentIntentId(@Param("stripePaymentIntentId") String stripePaymentIntentId);

    @Query("SELECT s FROM Order s WHERE s.customer = :customer")
    List<Order> findAllItemsBoughtByCustomerId(@Param("customer") UUID customer);
    @Query("SELECT s FROM Order s WHERE s.store = :store")
    List<Order> findAllItemsByStoreId(@Param("store")UUID store);
}
