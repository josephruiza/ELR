package es.ecommerce.prices.core.validator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = ProductValidator.class)
@Documented
public @interface ProductValid {
    String message() default "Product is not valid or does not exist.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
