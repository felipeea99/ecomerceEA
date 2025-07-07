package com.ecommerce.prorider.DTOs.update.payments;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class OrderUpdate {
    private UUID orderId;
    private LocalDateTime paymentDate;
    private BigDecimal totalAmount;
    private String stripePaymentIntentId;
    private String status;
    private UUID customerId;
    private int addressId;
}
