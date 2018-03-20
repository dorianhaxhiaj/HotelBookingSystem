package com.databridgeproject.constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import org.springframework.beans.factory.annotation.Autowired;

import com.databridgeproject.service.UserService;

/**
 * Field level annotated validation, that checks if the username for
 * UserForm class is unique.
 * 
 * @author Dorian Haxhiaj
 *
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UsernameUnique.UsernameUniqueValidator.class)
@Documented
public @interface UsernameUnique {

	String message() default "{com.databridgeproject.constraint.UsernameUnique.message}";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	
	class UsernameUniqueValidator implements ConstraintValidator<UsernameUnique, String> {
		
		@Autowired
		private UserService userService;
		
		public void initialize(UsernameUnique unique) {}
		
		public boolean isValid(String username, ConstraintValidatorContext context) {
			return !userService.isUsernameTaken(username);
		}
	}
}
