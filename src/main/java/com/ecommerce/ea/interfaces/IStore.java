package com.ecommerce.ea.interfaces;

import com.ecommerce.ea.DTOs.request.StoreRequest;
import com.ecommerce.ea.DTOs.response.StoreResponse;
import com.ecommerce.ea.DTOs.update.StoreUpdate;
import com.ecommerce.ea.entities.Store;
import com.ecommerce.ea.entities.UserAcc;

import java.util.UUID;

public interface IStore {
    Store findStoreByIdBaseForm(UUID storeId);
    Boolean createStore(UserAcc userAcc, StoreRequest storeRequest);
    StoreResponse editStore(StoreUpdate storeUpdate);
    Store findStoreByStoreName(String storeName);
    StoreResponse ToStoreResponse(Store store);
    Store ToStoreObj(StoreRequest storeRequest);
}
