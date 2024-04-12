package com.social.login.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.social.login.dto.UserRegistrationForm;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, UserRegistrationForm> {

	@Override
	public boolean isValid(final UserRegistrationForm user, final ConstraintValidatorContext context) {
		return user.getPassword().equals(user.getMatchingPassword());
	}

}
