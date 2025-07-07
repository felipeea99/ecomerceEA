package com.ecommerce.prorider.AOP_Functions;

import com.ecommerce.prorider.exceptions.BadRequestException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Set;

@Aspect
@Component
public class ValidatorAOP {

    private final Validator validator;

    public ValidatorAOP(Validator validator) {
        this.validator = validator;
    }


    @Before("execution(public * services..*.*(..))")
    public void validateMethodArguments(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();

        for (Object arg : args) {
            if (arg != null) {
                Set<ConstraintViolation<Object>> violations = validator.validate(arg);
                if (!violations.isEmpty()) {
                    StringBuilder sb = new StringBuilder();
                    for (ConstraintViolation<Object> violation : violations) {
                        sb.append(violation.getPropertyPath()).append(": ").append(violation.getMessage()).append("; ");
                    }
                    throw new BadRequestException(sb.toString());
                }
            }
        }
    }

}
