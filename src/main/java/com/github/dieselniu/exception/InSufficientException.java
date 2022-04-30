package com.github.dieselniu.exception;

public class InSufficientException extends RuntimeException {
	private final String value;

	public InSufficientException(String value) {
		this.value = value;
	}

	public String getOption() {
		return value;
	}
}
