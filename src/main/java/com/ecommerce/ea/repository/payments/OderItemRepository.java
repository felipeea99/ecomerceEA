package com.ecommerce.ea.repository.payments;

import com.ecommerce.ea.entities.payments.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OderItemRepository extends JpaRepository<OrderItem, UUID> {
}
