package com.ecommerce.ea.repository.payments;

import com.ecommerce.ea.DTOs.response.payments.SubscriptionHistoryResponse;
import com.ecommerce.ea.entities.payments.SubscriptionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SubscriptionHistoryRepository extends JpaRepository<SubscriptionHistory, Integer> {
    @Query("SELECT sh FROM SubscriptionHistory sh WHERE sh.subscription.store.storeId = :storeId")
    List<SubscriptionHistoryResponse> findAllSubscriptionHistoryByStoreId(@Param("storeId") UUID storeId);
}
