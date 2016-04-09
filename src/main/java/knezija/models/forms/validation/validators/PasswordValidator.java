package knezija.models.forms.validation.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import knezija.models.forms.validation.annotations.Password;


public class PasswordValidator implements ConstraintValidator<Password, String>{
	private Password passwordAnnotation;

	@Override
	public void initialize(Password passwordAnnotation) {
		this.passwordAnnotation = passwordAnnotation;
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(value.length()<6) {
			return false;
		}
		
		return true;
	}

}
