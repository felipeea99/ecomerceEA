package com.ecommerce.ea.entities.payments;

import com.ecommerce.ea.entities.auth.Store;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "subscriptions")
public class SubscriptionDb {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID subscriptionId;
    private String stripeCustomerId;
    private String stripePriceId;
    @Column(name = "stripe_subscription_id")
    private String stripeSubscriptionId;
    @Enumerated(EnumType.STRING)
    private StripeSubscriptionStatus status;
    private LocalDate startDate;
    private LocalDate endDate;
    @OneToOne
    @JoinColumn(name = "storeId")
    private Store store;
}

