package com.ecommerce.prorider.controllers.auth;

import com.ecommerce.prorider.services.auth.VerificationTokenService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/VerificationTokenController")
public class VerificationTokenController {
    private final VerificationTokenService verificationTokenService;

    public VerificationTokenController(VerificationTokenService verificationTokenService) {
        this.verificationTokenService = verificationTokenService;
    }
    @PostMapping("/authenticateTokenAccount")
    public Boolean authenticateTokenAccount(String token){
        /// Returns true or false
        return verificationTokenService.authenticateTokenAccount(token);
    }


}
