package com.ecommerce.prorider.entities.payments;

import com.ecommerce.prorider.entities.auth.UserAcc;
import com.ecommerce.prorider.entities.store.Address;
import com.ecommerce.prorider.entities.store.StatusType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID orderId;
    private LocalDateTime paymentDate;
    private BigDecimal totalAmount;
    private String stripePaymentIntentId;
    private StatusType status;
    private PaymentStatus paymentStatus;

    @ManyToOne
    private UserAcc userAcc;
    @ManyToOne
    private Address address;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;
}
