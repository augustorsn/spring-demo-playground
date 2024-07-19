package com.example.demo.validation;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class NotEmptyListValidator implements ConstraintValidator<NotEmptyList,List>  {

    @Override
    public boolean isValid(List value, ConstraintValidatorContext context) {
       return value != null && !value.isEmpty();
    }
    
}
