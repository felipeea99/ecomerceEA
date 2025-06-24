package com.ecommerce.ea.interfaces.auth;

import com.ecommerce.ea.entities.auth.Customer;
import com.ecommerce.ea.entities.auth.UserAcc;

import java.util.UUID;


public interface ICustomer {
    Customer addCustomer(UserAcc userAcc, String storeName);
    Customer findCustomerByUserIdAndStoreName(UUID userId, String storeName);
    Boolean customerCreationVerification(UUID userId, String storeName);
    Customer findCustomerById(UUID customerId);
}
