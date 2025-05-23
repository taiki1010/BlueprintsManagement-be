package Portfolio.BlueprintsManagement.presentation.exception.validation.addressValidation;

import Portfolio.BlueprintsManagement.presentation.dto.message.ErrorMessage;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class AddressValidator implements ConstraintValidator<ValidAddress, String> {

    @Override
    public boolean isValid(String address, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        return isValidCharCountLimit(address, context) && isValidBlank(address, context);
    }

    public boolean isValidCharCountLimit(String address, ConstraintValidatorContext context) {
        final boolean isCharCountUnderLimit = address.length() <= 161;
        if (!isCharCountUnderLimit) {
            context.buildConstraintViolationWithTemplate(ErrorMessage.CHAR_COUNT_ADDRESS_TOO_LONG.getMessage())
                    .addConstraintViolation();
        }
        return isCharCountUnderLimit;
    }

    public boolean isValidBlank(String address, ConstraintValidatorContext context) {
        final boolean isBlank = Objects.isNull(address) || address.isBlank();
        if (isBlank) {
            context.buildConstraintViolationWithTemplate(ErrorMessage.INPUT_FIELD_IS_BLANK.getMessage())
                    .addConstraintViolation();
        }
        return !isBlank;
    }
}
