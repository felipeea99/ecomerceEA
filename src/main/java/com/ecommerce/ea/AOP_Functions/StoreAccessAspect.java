package com.ecommerce.ea.AOP_Functions;

import com.ecommerce.ea.AOP_Functions.context.StoreContextHolder;
import com.ecommerce.ea.entities.auth.Customer;
import com.ecommerce.ea.entities.auth.Store;
import com.ecommerce.ea.services.auth.CustomerService;
import com.ecommerce.ea.services.auth.StoreService;
import com.ecommerce.ea.services.auth.UserAccService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Aspect
@Component
public class StoreAccessAspect {

    private final StoreService storeService;
    private final CustomerService customerService;
    private final UserAccService userAccService;

    public StoreAccessAspect(@Lazy StoreService storeService, @Lazy CustomerService customerService, @Lazy UserAccService userAccService) {
        this.storeService = storeService;
        this.customerService = customerService;
        this.userAccService = userAccService;
    }


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