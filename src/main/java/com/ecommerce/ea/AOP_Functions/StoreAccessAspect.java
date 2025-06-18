package com.ecommerce.ea.AOP_Functions;

import com.ecommerce.ea.AOP_Functions.context.StoreContextHolder;
import com.ecommerce.ea.entities.Customer;
import com.ecommerce.ea.entities.Store;
import com.ecommerce.ea.services.CustomerService;
import com.ecommerce.ea.services.StoreService;
import com.ecommerce.ea.services.UserAccService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Aspect
@Component
public class StoreAccessAspect {

    @Autowired
    private StoreService storeService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private UserAccService userAccService;

    @Before("@annotation(com.ecommerce.ea.annotations.ValidateStoreAccess)")
    public void validateStore(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();

        String storeName = null;
        UUID userId = null;

        for (Object arg : args) {
            if (arg instanceof String str && str.matches("[a-zA-Z0-9\\-]+")) {
                storeName = str;
            }
            if (arg instanceof UUID uuid) {
                userId = uuid;
            }
        }

        if (storeName == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing storeName");
        }
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing userId");
        }

        Store store = storeService.findStoreByStoreName(storeName);
        if (store == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Store not found: " + storeName);
        }
        /// Stores the storeId on the StoreContextHolder
        StoreContextHolder.setStoreId(store.getStoreId());

        /// Validates the userId and stores it on the StoreContextHolder
        userAccService.authenticateUserAccess(userId);
        StoreContextHolder.setUserId(userId);
        /// Retrieve the customer base on the userId and storeName
        Customer customer = customerService.findCustomerByUserIdAndStoreName(userId, storeName);

        StoreContextHolder.setCustomerId(customer.getCustomerId());
    }

    @After("@annotation(com.ecommerce.ea.annotations.ValidateStoreAccess)")
    public void clearContext() {
        StoreContextHolder.clear();
    }
}