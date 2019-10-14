package com.deltamate.demo.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD,ElementType.ANNOTATION_TYPE,ElementType.FIELD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AvailableUserIDValidator.class)
public @interface AvailableUserID {

    String message() default "User ID is already exit!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}