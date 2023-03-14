/**
 * Name: Amit Yadav
 * Copyright: Innobit Systems, 2021
 * Purpose: Api Validation Error
 */
package com.innobitsysytems.ipis.errors;

import java.util.Objects;

public class ApiValidationError extends ApiSubError {
	 private String object;
	    private String field;
	    private Object rejectedValue;
	    private String message;
	    
	    ApiValidationError(String object, String message) {
	        this.object = object;
	        this.message = message;
	    }
	    

		public ApiValidationError(String object, String field, Object rejectedValue, String message) {
			super();
			this.object = object;
			this.field = field;
			this.rejectedValue = rejectedValue;
			this.message = message;
		}


		public String getObject() {
			return object;
		}

		public void setObject(String object) {
			this.object = object;
		}

		public String getField() {
			return field;
		}

		public void setField(String field) {
			this.field = field;
		}

		public Object getRejectedValue() {
			return rejectedValue;
		}

		public void setRejectedValue(Object rejectedValue) {
			this.rejectedValue = rejectedValue;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		@Override
		public int hashCode() {
			return Objects.hash(field, message, object, rejectedValue);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ApiValidationError other = (ApiValidationError) obj;
			return Objects.equals(field, other.field) && Objects.equals(message, other.message)
					&& Objects.equals(object, other.object) && Objects.equals(rejectedValue, other.rejectedValue);
		}
		
	    
}
