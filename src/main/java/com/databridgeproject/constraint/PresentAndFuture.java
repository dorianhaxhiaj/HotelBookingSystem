package com.databridgeproject.constraint;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import org.apache.commons.lang.time.DateUtils;

import java.lang.annotation.*;
import java.util.Calendar;
import java.util.Date;


/**
 * Field level annotated validation, that checks if a date is today and
 * in the future.
 * 
 * @author Dorian Haxhiaj
 *
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PresentAndFuture.PresentAndFutureValidator.class)
@Documented
public @interface PresentAndFuture {
	
	String message() default "{com.databridgeproject.constraint.PresentAndFuture.message}";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	
	class PresentAndFutureValidator implements ConstraintValidator<PresentAndFuture, Date> {
		
		public void initialize(PresentAndFuture past) {
		}
		
		public boolean isValid(Date date, ConstraintValidatorContext context) {
			return date == null || !DateUtils.truncate(date, Calendar.DATE).before(DateUtils.truncate(new Date(), Calendar.DATE));
		}
	}
}
