package com.databridgeproject.constraint;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.Date;


/**
 * Class level validation that compared two date fields to see if the first date
 * comes before the second date.
 * 
 * @author Dorian Haxhiaj
 *
 */
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = FirstDateBeforeSecondDate.FirstDateBeforeSecondDateValidator.class)
@Documented
public @interface FirstDateBeforeSecondDate {

    String message() default "{com.databridge.constraint.FirstDateBeforeSecondDate.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * @return The first date field
     */
    String first();

    /**
     * @return The second date field
     */
    String second();
    
    @Target({TYPE, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        FirstDateBeforeSecondDate[] value();
    }

    public class FirstDateBeforeSecondDateValidator 
    	implements ConstraintValidator<FirstDateBeforeSecondDate, Object> {
        
    	/**
    	 * The variable name of the first date field.
    	 */
    	private String firstDateFieldName;
    	
    	/**
    	 * The variable name of the second date field.
    	 */
        private String secondDateFieldName;

        /**
         * Extracts both field names.
         */
        @Override
        public void initialize(final FirstDateBeforeSecondDate constraintAnnotation) {

            firstDateFieldName = constraintAnnotation.first();
            secondDateFieldName = constraintAnnotation.second();
        }

        /**
         * Checks if the first date field comes before the second date field.
         */
        @Override
        public boolean isValid(final Object value, final ConstraintValidatorContext context) {
    		
        	try {
        		Field firstDateField = ReflectionUtils.findField(value.getClass(), firstDateFieldName);
        		Field secondDateField = ReflectionUtils.findField(value.getClass(), secondDateFieldName);
        		
        		ReflectionUtils.makeAccessible(firstDateField);
        		ReflectionUtils.makeAccessible(secondDateField);
        		
        		final Date firstDate = (Date) firstDateField.get(value);
        		final Date secondDate = (Date) secondDateField.get(value);
                
                return firstDate == null || secondDate == null || firstDate.before(secondDate);
        	}
        	catch (final Exception e) {
        		
        	}
        	
        	return true;
        	 
        }
    }
}
