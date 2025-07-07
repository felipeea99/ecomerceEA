package com.ecommerce.prorider.interfaces.auth;

import com.ecommerce.prorider.entities.auth.VerificationToken;

public interface IVerificationToken {
    VerificationToken createToken(String storeName);
    Boolean authenticateTokenAccount(String token);

}
