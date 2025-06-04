package Portfolio.BlueprintsManagement.presentation.exception.validation.fileValidation;

import Portfolio.BlueprintsManagement.presentation.exception.validation.createdAtValidation.CreatedAtValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FileValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidFile {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
