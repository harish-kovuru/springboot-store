package com.springbootcode.store.validations;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LowecaseValidator.class)
public @interface Lowercase {
    String message() default "Invalid lower case";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
