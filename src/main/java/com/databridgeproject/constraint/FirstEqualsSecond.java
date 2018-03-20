package com.databridgeproject.constraint;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import org.springframework.util.ReflectionUtils;


/**
 * Class level annotated validation, that checks if the first field is equal
 * to the second field.
 * 
 * @author Dorian Haxhiaj
 *
 */
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = FirstEqualsSecond.FirstEqualsSecondValidator.class)
@Documented
public @interface FirstEqualsSecond {
	
	String message() default "{com.databridge.constraint.FirstEqualsSecond.message}";

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
        FirstEqualsSecond[] value();
    }
    
    /**
     * Validator that checks if a field of the class being annotated is equal to the
     * value of a second field. 
     * 
     * @author Dorian Haxhiaj
     *
     */
    class FirstEqualsSecondValidator implements ConstraintValidator<FirstEqualsSecond, Object> {

    	private String firstFieldName;
    	private String secondFieldName;
    	
		@Override
		public void initialize(FirstEqualsSecond constraintAnnotation) {
			
			firstFieldName = constraintAnnotation.first();
			secondFieldName = constraintAnnotation.second();
		}

		@Override
		public boolean isValid(Object value, ConstraintValidatorContext context) {
			
			try {
				
        		Field firstField = ReflectionUtils.findField(value.getClass(), firstFieldName);
        		Field secondField = ReflectionUtils.findField(value.getClass(), secondFieldName);
        		
        		ReflectionUtils.makeAccessible(firstField);
        		ReflectionUtils.makeAccessible(secondField);
        		
        		final Object firstObject = firstField.get(value);
        		final Object secondObject = secondField.get(value);
                
                return firstObject == null && secondObject == null || firstObject != null && firstObject.equals(secondObject);
        	}
        	catch (final Exception e) {
        		
        	}
        	
        	return true;
		}
    	
    }
}
