package Portfolio.BlueprintsManagement.presentation.exception.validation.createdAtValidation;

import Portfolio.BlueprintsManagement.presentation.exception.validation.blueprintNameValidation.BlueprintNameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CreatedAtValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCreatedAt {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
