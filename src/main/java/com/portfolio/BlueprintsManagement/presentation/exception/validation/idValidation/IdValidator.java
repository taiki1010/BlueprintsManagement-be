package com.portfolio.BlueprintsManagement.presentation.exception.validation.idValidation;

import com.portfolio.BlueprintsManagement.presentation.dto.message.ErrorMessage;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IdValidator implements ConstraintValidator<ValidId, String> {

    @Override
    public boolean isValid(String content, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        return isValidNaturalNumber(content, context);
    }

    public boolean isValidNaturalNumber(String id, ConstraintValidatorContext context) {
        final boolean isUUID = id.matches("^[a-f0-9]{8}-[a-f0-9]{4}-[1-5][a-f0-9]{3}-[89ab][a-f0-9]{3}-[a-f0-9]{12}$");
        if (!isUUID) {
            context.buildConstraintViolationWithTemplate(ErrorMessage.ID_MUST_BE_UUID.getMessage())
                    .addConstraintViolation();
        }
        return isUUID;
    }
}
