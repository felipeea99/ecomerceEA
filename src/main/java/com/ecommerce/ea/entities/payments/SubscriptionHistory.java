package com.ecommerce.ea.entities.payments;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class SubscriptionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int subscriptionHistoryId;
    @ManyToOne
    @JoinColumn(name = "subscription_id", nullable = false)
    private SubscriptionDb subscription;
}
