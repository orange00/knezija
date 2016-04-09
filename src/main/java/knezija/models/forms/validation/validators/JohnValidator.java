package knezija.models.forms.validation.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import knezija.models.forms.validation.annotations.John;

public class JohnValidator implements ConstraintValidator<John, String> {

	private John john;

	@Override
	public void initialize(John john) {
		this.john = john;
	}
	
	@Override
	public boolean isValid(String t, ConstraintValidatorContext cvc) {
		if (john.caseSensitive())
			return t.equals("John");
		else
			return t.equalsIgnoreCase("John");
	}

}