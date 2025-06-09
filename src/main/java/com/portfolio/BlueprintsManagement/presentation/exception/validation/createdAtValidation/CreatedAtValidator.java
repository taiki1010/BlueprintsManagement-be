package com.portfolio.BlueprintsManagement.presentation.exception.validation.createdAtValidation;

import com.portfolio.BlueprintsManagement.presentation.dto.message.ErrorMessage;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class CreatedAtValidator implements ConstraintValidator<ValidCreatedAt, String> {
    @Override
    public boolean isValid(String createdAt, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        return isValidBlank(createdAt, context) && isValidCreatedAtFormat(createdAt, context);
    }

    public boolean isValidBlank(String createdAt, ConstraintValidatorContext context) {
        final boolean isBlank = Objects.isNull(createdAt) || createdAt.isBlank();
        if (isBlank) {
            context.buildConstraintViolationWithTemplate(ErrorMessage.INPUT_FIELD_IS_BLANK.getMessage())
                    .addConstraintViolation();
        }
        return !isBlank;
    }

    public boolean isValidCreatedAtFormat(String createdAt, ConstraintValidatorContext context) {
        final boolean matchesDateFormat = createdAt.matches("^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$");
        if (!matchesDateFormat) {
            context.buildConstraintViolationWithTemplate(ErrorMessage.CREATED_AT_MUST_MATCH_FORMAT.getMessage())
                    .addConstraintViolation();
        }
        return matchesDateFormat;
    }
}
