package com.ecommerce.ea.repository.payments;

import com.ecommerce.ea.entities.payments.SubscriptionDb;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

public interface SubscriptionRepository extends JpaRepository<SubscriptionDb, UUID> {
    Optional<SubscriptionDb> findByStore_StoreId(UUID storeId);
    Optional<SubscriptionDb> findByStripeSubscriptionId(String stripeSubscriptionId);
}
