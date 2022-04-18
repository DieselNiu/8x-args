package com.github.dieselniu;

public class TooManyArgumentsException extends RuntimeException {
	private String value;

	public TooManyArgumentsException(String value) {
		this.value = value;
	}

	public String getOption() {
		return value;
	}
}