package com.github.dieselniu;

import com.github.dieselniu.exception.IllegalValueException;
import com.github.dieselniu.exception.InSufficientException;
import com.github.dieselniu.exception.TooManyArgumentsException;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

class OptionParsers {


	public static OptionParser<Boolean> bool() {
		return ((arguments, option) -> values(arguments, option, 0).isPresent());
	}

	public static <T> OptionParser<T> unary(T defaultValue, Function<String, T> valueParser) {
		return ((arguments, option) -> values(arguments, option, 1).map(it -> parseValue(option, it.get(0), valueParser)).orElse(defaultValue));
	}

	public static <T> OptionParser<T[]> list(IntFunction<T[]> generator, Function<String, T> valueParser) {
		return ((arguments, option) -> values(arguments, option)
			.map(it -> it.stream().map(value -> parseValue(option, value, valueParser))
				.toArray(generator)).orElse(generator.apply(0)));
	}

	private static Optional<List<String>> values(List<String> arguments, Option option) {
		int index = arguments.indexOf("-" + option.value());
		List<String> values = values(arguments, index);
		return Optional.ofNullable(index == -1 ? null : values);
	}


	private static Optional<List<String>> values(List<String> arguments, Option option, int expectedSize) {

		return values(arguments, option).map(it -> checkSize(option, expectedSize, it));
	}


	private static List<String> checkSize(Option option, int expectedSize, List<String> values) {
		if (values.size() < expectedSize) throw new InSufficientException(option.value());
		if (values.size() > expectedSize) throw new TooManyArgumentsException(option.value());
		return values;
	}

	static <T> T parseValue(Option option, String value, Function<String, T> parser) {
		try {
			return parser.apply(value);
		} catch (Exception e) {
			throw new IllegalValueException(option.value(), value);
		}
	}


	private static List<String> values(List<String> arguments, int index) {
		return arguments.subList(index + 1, IntStream.range(index + 1, arguments.size())
			.filter(it -> arguments.get(it).matches("^-[a-zA-Z-]+$"))
			.findFirst().orElse(arguments.size()));
	}

}
