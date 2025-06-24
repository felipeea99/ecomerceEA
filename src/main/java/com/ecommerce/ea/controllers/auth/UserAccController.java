package com.ecommerce.ea.controllers.auth;

import com.ecommerce.ea.DTOs.response.auth.LoginDTO;
import com.ecommerce.ea.entities.auth.UserAcc;
import com.ecommerce.ea.services.auth.UserAccService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
public class UserAccController {

    @Autowired
    private UserAccService userAccService;

    // EndPoint for registration of Users

    @PostMapping("/{storeName}")
    public Boolean register(@RequestBody UserAcc user, @PathVariable String storeName){
        return userAccService.register(user, storeName);
    }

    // EndPoint for registration of StoreUsers
//    @PostMapping("/registerStoreUser")
//    public UserAcc registerStoreUser(@RequestBody UserAcc user){
//        return userAccService.registerStoreUser(user);
//    }

    /// Login
    @PostMapping("/login")
    public String login(@RequestBody LoginDTO userLogin){
        return userAccService.verify(userLogin);
    }

}
