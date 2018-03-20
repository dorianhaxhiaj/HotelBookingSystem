package com.databridgeproject.constraint;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import org.springframework.beans.factory.annotation.Autowired;

import com.databridgeproject.dto.RoomForm;
import com.databridgeproject.service.RoomService;

@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RoomNumberUniqueInHotel.RoomNumberUniqueInHotelValidator.class)
@Documented
public @interface RoomNumberUniqueInHotel {

	String message() default "{com.databridgeproject.constraint.RoomNumberUniqueInHotel.message}";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	
	class RoomNumberUniqueInHotelValidator implements 
		ConstraintValidator<RoomNumberUniqueInHotel, RoomForm> {

		@Autowired
		RoomService roomService;
		
		@Override
		public void initialize(RoomNumberUniqueInHotel constraintAnnotation) {}

		@Override
		public boolean isValid(RoomForm roomForm, ConstraintValidatorContext context) {
			
			if (roomForm != null && roomService.isRoomNumberUniqueInHotel(roomForm)) {
				return true;
			}
			
			return false;
		}
	}
}
