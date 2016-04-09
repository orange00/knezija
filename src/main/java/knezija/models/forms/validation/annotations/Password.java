package knezija.models.forms.validation.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotNull;

import knezija.models.forms.validation.validators.PasswordValidator;

@Documented
@Constraint(validatedBy = { PasswordValidator.class })
@Target({ METHOD, FIELD, PARAMETER })
@Retention(RUNTIME)
@ReportAsSingleViolation
@NotNull
public @interface Password {
	String message() default "";
	 
    Class<?>[] groups() default {};
 
    Class<? extends Payload>[] payload() default {};
}
