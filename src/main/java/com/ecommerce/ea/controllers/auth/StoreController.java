package com.ecommerce.ea.controllers.auth;

import com.ecommerce.ea.AOP_Functions.annotations.ValidateStoreAccess;
import com.ecommerce.ea.DTOs.request.auth.StoreRequest;
import com.ecommerce.ea.DTOs.response.auth.StoreResponse;
import com.ecommerce.ea.DTOs.update.StoreUpdate;
import com.ecommerce.ea.entities.auth.UserAcc;
import com.ecommerce.ea.services.auth.StoreService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/storeController")
public class StoreController {
    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @ValidateStoreAccess
    @PostMapping
    public Boolean createStore(@RequestBody UserAcc userAcc, @RequestBody StoreRequest storeRequest){
        return this.storeService.createStore(userAcc,storeRequest);
    }

    @ValidateStoreAccess
    @PutMapping
    public StoreResponse editStore(@RequestBody StoreUpdate storeUpdate){
        return this.storeService.editStore(storeUpdate);
    }
}
