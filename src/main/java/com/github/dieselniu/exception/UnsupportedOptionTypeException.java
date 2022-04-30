package com.github.dieselniu.exception;

public class UnsupportedOptionTypeException extends RuntimeException{

	private String value;
	private Class<?> type;

	public UnsupportedOptionTypeException(String value, Class<?> type) {
		this.value = value;
		this.type = type;
	}
}
