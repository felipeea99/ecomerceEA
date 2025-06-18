package com.ecommerce.ea.interfaces;

import com.ecommerce.ea.entities.Customer;
import com.ecommerce.ea.entities.UserAcc;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;


public interface ICustomer {
    Customer addCustomer(UserAcc userAcc, String storeName);
    Customer findCustomerByUserIdAndStoreName(UUID userId, String storeName);
    Boolean customerCreationVerification(UUID userId, String storeName);
    Customer findCustomerById(UUID customerId);
}
