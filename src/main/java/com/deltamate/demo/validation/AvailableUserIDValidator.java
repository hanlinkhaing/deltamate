package com.deltamate.demo.validation;


import com.deltamate.demo.service.UserService;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AvailableUserIDValidator implements ConstraintValidator<AvailableUserID, String> {

    private UserService userService;

    public AvailableUserIDValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try{
            userService.findUserByID(s);
            return false;
        } catch (EntityNotFoundException e) {
            return true;
        }
    }
}
