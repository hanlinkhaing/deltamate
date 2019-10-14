package com.deltamate.demo.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD,ElementType.ANNOTATION_TYPE,ElementType.FIELD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordsMatchingValidator.class)
public @interface PasswordsMatching {

    String message() default "Passwords are not same!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
