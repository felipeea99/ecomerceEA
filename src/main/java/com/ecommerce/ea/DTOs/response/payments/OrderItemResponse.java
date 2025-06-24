package com.ecommerce.ea.DTOs.response.payments;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemResponse {
        private String productName;
        private int quantity;
        private BigDecimal price;
}
