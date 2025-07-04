package com.portfolio.BlueprintsManagement.presentation.exception.validation.blueprintNameValidation;

import com.portfolio.BlueprintsManagement.presentation.dto.message.ErrorMessage;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;

public class BlueprintNameValidator implements ConstraintValidator<ValidBlueprintName, String> {

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        return isValidBlank(name, context) && isValidCharCountLimit(name, context);
    }

    public boolean isValidBlank(String name, ConstraintValidatorContext context) {
        final boolean isBlank = Objects.isNull(name) || name.isBlank();
        if (isBlank) {
            context.buildConstraintViolationWithTemplate(
                            ErrorMessage.INPUT_FIELD_IS_BLANK.getMessage())
                    .addConstraintViolation();
        }
        return !isBlank;
    }

    public boolean isValidCharCountLimit(String name, ConstraintValidatorContext context) {
        final boolean isCharCountUnderLimit = name.length() <= 20;
        if (!isCharCountUnderLimit) {
            context.buildConstraintViolationWithTemplate(
                            ErrorMessage.CHAR_COUNT_BLUEPRINT_NAME_TOO_LONG.getMessage())
                    .addConstraintViolation();
        }
        return isCharCountUnderLimit;
    }

}
