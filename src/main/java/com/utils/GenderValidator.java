package com.utils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by Zveriki on 27.05.2017.
 */
public class GenderValidator implements ConstraintValidator<Gender, String> {

    String gender;

    @Override
    public void initialize(Gender gender) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return (s != null && (s.toLowerCase().equals("male") || s.toLowerCase().equals("female")));
    }
}
