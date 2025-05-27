package com.ecommerce.ea.controllers;

import com.ecommerce.ea.DTOs.LoginDTO;
import com.ecommerce.ea.entities.UserAcc;
import com.ecommerce.ea.services.UserAccService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAccController {

    @Autowired
    private UserAccService userAccService;


    @PostMapping("/api/acc/register")
    public UserAcc register(@RequestBody UserAcc user){

        return userAccService.register(user);
    }

    @PostMapping("/api/acc/login")
    public String login(@RequestBody LoginDTO userLogin){
        return userAccService.verify(userLogin);
    }


}
