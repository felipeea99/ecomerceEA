package com.ecommerce.ea.AOP_Functions.context;

import java.util.UUID;

// StoreContextHolder.java
public class StoreContextHolder {
    private static final ThreadLocal<UUID> storeIdHolder = new ThreadLocal<>();
    private static final ThreadLocal<UUID> customerIdHolder = new ThreadLocal<>();
    private static final ThreadLocal<UUID> userIdHolder = new ThreadLocal<>();

    public static void setStoreId(UUID storeId) {
        storeIdHolder.set(storeId);
    }
    public static UUID getStoreId() {
        return storeIdHolder.get();
    }

    public static void setUserId(UUID userId) {
        userIdHolder.set(userId);
    }
    public static UUID getUserId() {
        return userIdHolder.get();
    }

    public static void setCustomerId(UUID customerId) {
        customerIdHolder.set(customerId);
    }
    public static UUID getCustomerId() {
        return customerIdHolder.get();
    }

    public static void clear() {
        storeIdHolder.remove();
        customerIdHolder.remove();
        userIdHolder.remove();
    }
}
