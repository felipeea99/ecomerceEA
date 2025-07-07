package com.ecommerce.prorider.entities.payments;

import com.ecommerce.prorider.entities.store.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID orderItemId;
    @ManyToOne
    private Order order;
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;
    private int quantity;
    private BigDecimal unitPrice;
}


