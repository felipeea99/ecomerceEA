package com.ecommerce.ea.services;

import com.ecommerce.ea.DTOs.LoginDTO;
import com.ecommerce.ea.entities.UserAcc;
import com.ecommerce.ea.interfaces.IUserAcc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
public class UserAccService {

    @Autowired
    private IUserAcc _userAcc;
    @Autowired
    private JWTService jwtService;
    @Autowired
    AuthenticationManager authManager;


    //Password encryption object and the strength is the rounds hashed
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Transactional
    public UserAcc register(UserAcc userAcc){
        //Set values to lowe case
        userAcc.setEmail(userAcc.getEmail().toLowerCase());
        userAcc.setName(userAcc.getName().toLowerCase());
        userAcc.setUserLastName1(userAcc.getUserLastName1().toLowerCase());
        userAcc.setUserLastName2(userAcc.getUserLastName2().toLowerCase());
        userAcc.setUserName(userAcc.getEmail());// sets the userName to the value of email

        //password encryption
        userAcc.setPassword(encoder.encode(userAcc.getPassword()));
        return _userAcc.save(userAcc);
    }


    public String verify(LoginDTO user) {
        Authentication authentication =
                authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));

        if(authentication.isAuthenticated()){
            return jwtService.generateToken(user.getUserName());
        }
        return "Fail";
    }


}
