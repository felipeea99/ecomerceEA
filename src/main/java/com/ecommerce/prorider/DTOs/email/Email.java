package com.ecommerce.prorider.DTOs.email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Email {
    private String fromAddress;
    private String toAddress;
    private String subject;
    private String body;
}
