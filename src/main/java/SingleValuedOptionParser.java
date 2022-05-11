import exception.IllegalValueException;
import exception.InSufficientException;
import exception.TooManyArgumentException;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.IntStream;

class SingleValuedOptionParser {


	public static OptionParser<Boolean> bool() {
		return ((arguments, option) -> getValues(arguments, option, 0).isPresent());
	}

	public static <T> OptionParser<T> unary(T defaultValue, Function<String, T> valueParser) {
		return ((arguments, option) -> getValues(arguments, option, 1).map(it -> parseValue(option, it.get(0), valueParser)).orElse(defaultValue));
	}


	private static <T> T parseValue(Option option, String value, Function<String, T> valueParser) {
		try {
			return valueParser.apply(value);
		} catch (Exception e) {
			throw new IllegalValueException(option.value(), value);
		}
	}

	private static Optional<List<String>> getValues(List<String> arguments, Option option, int expectedSize) {
		int index = arguments.indexOf("-" + option.value());
		if (index == -1) return Optional.empty();
		List<String> values = getValues(arguments, index);
		if (values.size() < expectedSize) throw new InSufficientException(option.value());
		if (values.size() > expectedSize) throw new TooManyArgumentException(option.value());
		return Optional.of(values);
	}

	private static List<String> getValues(List<String> arguments, int index) {
		return arguments.subList(index + 1, IntStream.range(index + 1, arguments.size())
			.filter(it -> arguments.get(it).startsWith("-"))
			.findFirst()
			.orElse(arguments.size()));
	}

}
