package com.ecommerce.ea.interfaces.auth;

import com.ecommerce.ea.DTOs.request.auth.StoreRequest;
import com.ecommerce.ea.DTOs.response.auth.StoreResponse;
import com.ecommerce.ea.DTOs.update.StoreUpdate;
import com.ecommerce.ea.entities.auth.Store;
import com.ecommerce.ea.entities.auth.UserAcc;

import java.util.UUID;

public interface IStore {
    Store findStoreByIdBaseForm(UUID storeId);
    Boolean createStore(UserAcc userAcc, StoreRequest storeRequest);
    StoreResponse editStore(StoreUpdate storeUpdate);
    Store findStoreByStoreName(String storeName);
    StoreResponse ToStoreResponse(Store store);
    Store ToStoreObj(StoreRequest storeRequest);
    void addStripeAccount(String stripeAccountId, String storeName);
    void markOnboardingComplete(String stripeAccountId);
}
