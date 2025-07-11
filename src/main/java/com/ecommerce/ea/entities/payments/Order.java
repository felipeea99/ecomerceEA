package com.ecommerce.ea.entities.payments;

import com.ecommerce.ea.entities.store.Address;
import com.ecommerce.ea.entities.auth.Customer;
import com.ecommerce.ea.entities.store.StatusType;
import com.ecommerce.ea.entities.auth.Store;
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
    private Customer customer;
    @ManyToOne
    private Address address;
    @ManyToOne
    private Store store;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;
}
