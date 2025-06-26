package com.ecommerce.ea.DTOs.response.payments;

import com.ecommerce.ea.DTOs.response.store.AddressResponse;
import com.ecommerce.ea.DTOs.response.store.StoreLiteResponse;
import com.ecommerce.ea.entities.auth.Customer;
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
public class OrderResponse {
    private UUID orderId;
    private String status;
    private BigDecimal totalAmount;
    private LocalDateTime paymentDate;
    private List<OrderItemResponse> items;
    private String storeName;
    private String paymentStatus;
    private Customer customer;
    private StoreLiteResponse store;
    private AddressResponse address;
}