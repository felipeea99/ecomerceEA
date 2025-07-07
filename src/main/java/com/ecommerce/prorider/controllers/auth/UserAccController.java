package com.ecommerce.prorider.controllers.auth;

import com.ecommerce.prorider.DTOs.request.auth.UserAccRequest;
import com.ecommerce.prorider.DTOs.response.auth.LoginDTO;
import com.ecommerce.prorider.entities.auth.UserAcc;
import com.ecommerce.prorider.services.auth.UserAccService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
public class UserAccController {

    private final UserAccService userAccService;

    public UserAccController(UserAccService userAccService) {
        this.userAccService = userAccService;
    }

    // EndPoint for registration of Users
    @PostMapping("/")
    public Boolean register(@RequestBody UserAccRequest userAccRequest){
        return userAccService.register(userAccRequest);
    }

    /// EndPoint for registration of StoreUsers
    @PostMapping("/registerStoreUser")
    public UserAcc registerStoreUser(@RequestBody UserAccRequest userAccRequest){
        return userAccService.registerStoreUser(userAccRequest);
    }

    /// Login
    @PostMapping("/login")
    public String login(@RequestBody LoginDTO userLogin){
        return userAccService.verify(userLogin);
    }

}
