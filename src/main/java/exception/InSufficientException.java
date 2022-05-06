package exception;

public class InSufficientException extends RuntimeException {
	private String option;

	public InSufficientException(String option) {
		this.option = option;
	}

	public String getOption() {
		return option;
	}
}
