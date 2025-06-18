package com.ecommerce.ea.services;

import com.ecommerce.ea.DTOs.request.StoreRequest;
import com.ecommerce.ea.DTOs.response.StoreResponse;
import com.ecommerce.ea.DTOs.update.StoreUpdate;
import com.ecommerce.ea.entities.RoleType;
import com.ecommerce.ea.entities.Store;
import com.ecommerce.ea.entities.UserAcc;
import com.ecommerce.ea.exceptions.BadRequestException;
import com.ecommerce.ea.interfaces.IStore;
import com.ecommerce.ea.repository.StoreRepository;
import com.ecommerce.ea.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class StoreService implements IStore {

    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final UserAccService userAccService;

    public StoreService(StoreRepository storeRepository, UserRepository userRepository, UserAccService userAccService){
        this.storeRepository = storeRepository;
        this.userRepository = userRepository;
        this.userAccService = userAccService;
    }

    @Override
    public Store findStoreByIdBaseForm(UUID storeId) {
        return storeRepository.findById(storeId).orElseThrow(()-> new BadRequestException("storeId was not found on the database"));
    }

    /// It is the main method to create the Store Object
    ///This method creates first a userObject then base on the store email, it modifies the acc email and username to its store.Email value
    @Override
    public Boolean createStore(UserAcc userAcc, StoreRequest storeRequest) {
        /// Calls the method of "registerStoreUser" from the userAccService and save it the "userAccObj"
        UserAcc userAccObj = userAccService.registerStoreUser(userAcc);
        ///Transform the storeRequest into Store type
        Store store = this.ToStoreObj(storeRequest);
        ///change the userAcc attributes to the ones of store-email
        userAccObj.setEmail(store.getStoreEmail());
        userAccObj.setUserName(store.getStoreEmail());
        userAccObj.setRole(RoleType.STORE);
        /// Saves the user
        UserAcc userSaved = userRepository.save(userAccObj);
        /// Saves the store
        Store storeSaved = storeRepository.save(store);

        return true;
    }
    ///This method edits a store Object
    @Override
    public StoreResponse editStore(StoreUpdate storeUpdate) {
        /// UserId validation
        userAccService.authenticateUserAccess(storeUpdate.getUserId());

        /// StoreEdit Modification
        Store store = new Store();
        store.setStoreName(storeUpdate.getStoreName());
        store.setStoreEmail(storeUpdate.getStoreEmail());
        store.setDomain(storeUpdate.getDomain());
        /// Transform it into "storeResponse" type
        return this.ToStoreResponse(store);
    }

    //Finds the Store Object base on the storeName
    @Override
    public Store findStoreByStoreName(String storeName) {
        return storeRepository.findStoreByStoreNameDb(storeName);
    }

    @Override
    public StoreResponse ToStoreResponse(Store store) {
        StoreResponse storeResponse = new StoreResponse();

        storeResponse.setStoreID(store.getStoreId());
        storeResponse.setStoreEmail(store.getStoreEmail());
        storeResponse.setDomain(store.getDomain());
        storeResponse.setStoreName(store.getStoreName());

        return storeResponse;
    }

    /// This method does not transform the store.setUser, it is given already by "createStore" method
    @Override
    public Store ToStoreObj(StoreRequest storeRequest) {
        Store store = new Store();
        store.setStoreName(storeRequest.getStoreName());
        store.setStoreEmail(storeRequest.getStoreEmail());
        store.setDomain(storeRequest.getDomain());

        return store;
    }




}
