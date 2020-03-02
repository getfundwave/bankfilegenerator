package com.bankfilegenerator.validator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Arrays;
import com.bankfilegenerator.annotation.Equals;
import com.bankfilegenerator.annotation.Length;
import com.bankfilegenerator.annotation.MaxLength;
import com.bankfilegenerator.annotation.MultiValue;
import com.bankfilegenerator.annotation.NotEmpty;
import com.bankfilegenerator.annotation.NotNull;
import com.bankfilegenerator.annotation.NonNegative;
import com.bankfilegenerator.exception.DefaultValueException;
import com.bankfilegenerator.exception.InputLengthException;
import com.bankfilegenerator.exception.MaxLengthException;
import com.bankfilegenerator.exception.MultiValueException;
import com.bankfilegenerator.exception.NotEmptyException;
import com.bankfilegenerator.exception.NotNullException;
import com.bankfilegenerator.exception.PositiveOnlyException;

public class GenericValidator<T> {
	
	private T obj;
	private String investorName;
	
	public GenericValidator(T obj, String investorName) {
		this.obj = obj;
		this.investorName = investorName;
	}
	
	private String removeDashes(String input) {
		return input.replace("-", "");
	}
	
	public GenericValidator(T obj) {
		this.obj = obj;
		this.investorName = "";
	}
	
	public void validate() throws InputLengthException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, MultiValueException, PositiveOnlyException, NotEmptyException, MaxLengthException, DefaultValueException, NotNullException {
		
		Class<T> obj = (Class<T>) this.obj.getClass();
		
		for(Method method: obj.getDeclaredMethods()) {
			
			if(method.isAnnotationPresent(NotEmpty.class)) {
				method.setAccessible(true);
				NotEmpty notEmpty = method.getAnnotation(NotEmpty.class);
				String value = (String) method.invoke(this.obj);
				if(value.length() == 0 || value.replace(" ", "").length() == 0) {
					throw new NotEmptyException("Notice: " + this.investorName + " (" + notEmpty.name() + " must not be empty or contain only spaces)");
				}
			}
			
			if(method.isAnnotationPresent(NotNull.class)) {
				method.setAccessible(true);
				NotNull notNull = method.getAnnotation(NotNull.class);
				String value = (String) method.invoke(this.obj);
				if(value == null) {
					throw new NotNullException("Notice: " + this.investorName + " (" + notNull.name() + " must not be null)");
				}
			}
			
			if(method.isAnnotationPresent(Equals.class)) {
				method.setAccessible(true);
				Equals default1 = method.getAnnotation(Equals.class);
				String value = (String) method.invoke(this.obj);
				if(!value.equalsIgnoreCase(default1.value())){
					throw new DefaultValueException("Notice: " + this.investorName + " (" + default1.name() + " must always be " + default1.value() + ")");
				}
			}
			
			if(method.isAnnotationPresent(Length.class)) {
				method.setAccessible(true);
				Length inputLengthConstriant = method.getAnnotation(Length.class);
				int requiredLength = inputLengthConstriant.length();
				String value = removeDashes((String) method.invoke(this.obj));
				if(value.length() != requiredLength) {
					throw new InputLengthException("Notice: " + this.investorName + " (" + inputLengthConstriant.name() + " must be " + requiredLength + " character long excluding any dashes(if any))");
				}					
			}
			
			if(method.isAnnotationPresent(MultiValue.class)) {
				method.setAccessible(true);
				MultiValue multiValue = method.getAnnotation(MultiValue.class);
				String[] requiredValues = multiValue.values();
				String requiredValueString = String.join(", ", requiredValues);
				String value = (String) method.invoke(this.obj);
				if(!Arrays.asList(requiredValues).contains(value)) {
					throw new MultiValueException("Notice: " + this.investorName + " (" + multiValue.name() + " must be one of " + requiredValueString + ")");
				}
			}
			
			if(method.isAnnotationPresent(NonNegative.class)) {
				method.setAccessible(true);
				NonNegative positiveOnly = method.getAnnotation(NonNegative.class);
				BigDecimal value = (BigDecimal) method.invoke(this.obj);
				if(value.compareTo(BigDecimal.ZERO) < 0) {
					throw new PositiveOnlyException("Notice: " + this.investorName + " (" + positiveOnly.name() + " must be greater than 0)");
				}
			}
			
			if(method.isAnnotationPresent(MaxLength.class)) {
				method.setAccessible(true);
				MaxLength maxLength = method.getAnnotation(MaxLength.class);
				String value = (String) method.invoke(this.obj);
				if(value.length() > maxLength.length()) {
					throw new MaxLengthException("Notice: " + this.investorName + " (" + maxLength.name() + " should be " + maxLength.length() + " character long at max)");
				}
			}
			
		}	
	}
	
}
