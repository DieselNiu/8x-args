package exception;

public class IllegalValueException extends RuntimeException {
	private String optionValue;
	private String value;

	public IllegalValueException(String optionValue, String value) {
		this.optionValue = optionValue;
		this.value = value;
	}
}
