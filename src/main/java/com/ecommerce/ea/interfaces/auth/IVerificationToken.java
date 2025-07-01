package com.ecommerce.ea.interfaces.auth;

import com.ecommerce.ea.entities.auth.VerificationToken;

public interface IVerificationToken {
    VerificationToken createToken(String storeName);
    Boolean authenticateTokenAccount(String token);

}
