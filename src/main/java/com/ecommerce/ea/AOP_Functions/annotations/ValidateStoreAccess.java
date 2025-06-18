package com.ecommerce.ea.AOP_Functions.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) //applies to all the methods
@Retention(RetentionPolicy.RUNTIME)  // Available en time execution AOP
public @interface ValidateStoreAccess {
}
