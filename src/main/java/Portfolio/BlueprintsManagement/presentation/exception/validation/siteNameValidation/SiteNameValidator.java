package Portfolio.BlueprintsManagement.presentation.exception.validation.siteNameValidation;

import Portfolio.BlueprintsManagement.presentation.exception.message.ErrorMessage;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SiteNameValidator implements ConstraintValidator<ValidSiteName, String> {

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        return isValidCharCountLimit(name, context);
    }

    public boolean isValidCharCountLimit(String name, ConstraintValidatorContext context) {
        final boolean isCharCountUnderLimit = name.length() <= 50;
        if (!isCharCountUnderLimit) {
            context.buildConstraintViolationWithTemplate(ErrorMessage.CHAR_COUNT_SITE_NAME_TOO_LONG.getMessage())
                    .addConstraintViolation();
        }
        return isCharCountUnderLimit;
    }
}
