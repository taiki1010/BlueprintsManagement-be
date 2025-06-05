package com.portfolio.BlueprintsManagement.presentation.exception.validation.remarkValidation;

import com.portfolio.BlueprintsManagement.presentation.dto.message.ErrorMessage;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RemarkValidator implements ConstraintValidator<ValidRemark, String> {

    @Override
    public boolean isValid(String remark, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        return isValidCharCountLimit(remark, context);
    }

    public boolean isValidCharCountLimit(String remark, ConstraintValidatorContext context) {
        final boolean isCharCountUnderLimit = remark.length() <= 200;
        if (!isCharCountUnderLimit) {
            context.buildConstraintViolationWithTemplate(ErrorMessage.CHAR_COUNT_REMARK_TOO_LONG.getMessage())
                    .addConstraintViolation();
        }
        return isCharCountUnderLimit;
    }
}
