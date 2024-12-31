package com.youcode.aptio.validation;

import com.youcode.aptio.util.TimezoneUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IsValidTimezoneValidator implements ConstraintValidator<IsValidTimezone, String> {

    @Override
    public void initialize(IsValidTimezone constraintAnnotation) {
    }

    @Override
    public boolean isValid(String timezone, ConstraintValidatorContext context) {
        return TimezoneUtils.isValidTimezone(timezone);
    }
}