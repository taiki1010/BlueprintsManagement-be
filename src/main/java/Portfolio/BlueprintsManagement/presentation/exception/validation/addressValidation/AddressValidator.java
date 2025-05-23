package Portfolio.BlueprintsManagement.presentation.exception.validation.addressValidation;

import Portfolio.BlueprintsManagement.presentation.exception.message.ErrorMessage;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AddressValidator implements ConstraintValidator<ValidAddress, String> {

    @Override
    public boolean isValid(String address, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        return isValidCharCountLimit(address, context);
    }

    public boolean isValidCharCountLimit(String address, ConstraintValidatorContext context) {
        final boolean isCharCountUnderLimit = address.length() <= 161;
        if (!isCharCountUnderLimit) {
            context.buildConstraintViolationWithTemplate(ErrorMessage.CHAR_COUNT_ADDRESS_TOO_LONG.getMessage())
                    .addConstraintViolation();
        }
        return isCharCountUnderLimit;
    }
}
