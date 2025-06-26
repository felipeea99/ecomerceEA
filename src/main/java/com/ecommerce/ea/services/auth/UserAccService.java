package com.ecommerce.ea.services.auth;

import com.ecommerce.ea.DTOs.response.auth.LoginDTO;
import com.ecommerce.ea.DTOs.response.auth.UserResponse;
import com.ecommerce.ea.entities.auth.UserAcc;
import com.ecommerce.ea.entities.auth.UserAccPrincipal;
import com.ecommerce.ea.exceptions.BadRequestException;
import com.ecommerce.ea.exceptions.UnauthorizedException;
import com.ecommerce.ea.interfaces.auth.IUserAcc;
import com.ecommerce.ea.services.store.JWTService;
import com.stripe.model.tax.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserAccService {

    @Autowired
    private IUserAcc _userAcc;
    @Autowired
    private JWTService jwtService;
    @Autowired
    AuthenticationManager authManager;
    @Autowired
    private CustomerService customerService;


    //Password encryption object and the strength is the rounds hashed
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    /// User-Store Creation, returns the object saved to be available to used on the StoreService on "createStore" method
    @Transactional
    public UserAcc registerStoreUser(UserAcc userAcc){
        /// Email Validation, checks if exist or not
        String email = userAcc.getEmail().toLowerCase();
        UserAcc existingUser = _userAcc.findByUserName(email);
        if (existingUser != null) {
            throw new BadRequestException("Email already exists");
        }
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

    /// User Creation, receives a storeName from the route, then it creates a customer object on the database (customerId, User, store)
    @Transactional
    public Boolean register(UserAcc userAcc, String storeName) {
        /// Email Validation, checks if exist or not
        String email = userAcc.getEmail().toLowerCase();
        UserAcc existingUser = _userAcc.findByUserName(email);
        if (existingUser != null) {
            throw new BadRequestException("Email already exists");
        }

        //Set values to lowe case
        userAcc.setEmail(userAcc.getEmail().toLowerCase());
        userAcc.setName(userAcc.getName().toLowerCase());
        userAcc.setUserLastName1(userAcc.getUserLastName1().toLowerCase());
        userAcc.setUserLastName2(userAcc.getUserLastName2().toLowerCase());
        userAcc.setUserName(userAcc.getEmail());// sets the userName to the value of email

        //password encryption
        userAcc.setPassword(encoder.encode(userAcc.getPassword()));
        _userAcc.save(userAcc);

        //Create the Customer Object
       Boolean result = customerService.customerCreationVerification(userAcc.getUserId(), storeName);
        if (result){
            throw new BadRequestException("Customer already exists in that store");
        }else{
            customerService.addCustomer(userAcc,storeName);
        }
        return true;
    }


    public String verify(LoginDTO user) {
        Authentication authentication =
                authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));

        if(authentication.isAuthenticated()){
            return jwtService.generateToken(user.getUserName());
        }
        return "Fail";
    }

    public UUID authenticateUserAccess(UUID resourceOwnerId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException("User is not authenticated");
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserAccPrincipal userPrincipal) {
            UUID loggedUserId = userPrincipal.getUserId();
            String role = userPrincipal.getRole();

            if (loggedUserId.equals(resourceOwnerId)) {
                /// the userId matches, so it gets the userId
                return loggedUserId;
            }

            if ("ADMIN".equalsIgnoreCase(role)) {
                /// It has admin role so it gets the UserId
                return loggedUserId;
            }

            // userId does not match and role is NOT ADMIN
            throw new UnauthorizedException("You cant do this action");
        }

        throw new UnauthorizedException("Invalid user");
    }

    public UserResponse ToUserResponse(UserAcc userAcc){
        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(userAcc.getUserId());
        userResponse.setUserName(userAcc.getUserName());
        userResponse.setName(userAcc.getName());
        userResponse.setUserLastName1(userAcc.getUserLastName1());
        userResponse.setUserLastName2(userAcc.getUserLastName2());
        userResponse.setPhoneNumber(userAcc.getPhoneNumber());
        userResponse.setEmail(userAcc.getEmail());
        return userResponse;
    }








}
