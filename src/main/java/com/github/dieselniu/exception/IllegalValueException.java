package com.github.dieselniu.exception;

public class IllegalValueException extends RuntimeException{

	private String value;
	private String value1;

	public IllegalValueException(String value, String value1) {

		this.value = value;
		this.value1 = value1;
	}

	public String getParameter() {
		return value;
	}
}
