package com.github.dieselniu;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.IntStream;

class OptionParsers {


	public static OptionParser<Boolean> bool() {
		return ((arguments, option) -> values(arguments, option, 0).isPresent());
	}

	public static <T> OptionParser<T> unary(T defaultValue, Function<String, T> valueParser) {
		return ((arguments, option) -> values(arguments, option, 1).map(it -> parseValue(it.get(0), valueParser)).orElse(defaultValue));
	}


	static <T> T parseValue(String value, Function<String, T> parser) {
		return parser.apply(value);
	}


	private static Optional<List<String>> values(List<String> arguments, Option option, int expectedSize) {
		int index = arguments.indexOf("-" + option.value());
		if (index == -1) return Optional.empty();
		List<String> values = values(arguments, index);

		if (values.size() < expectedSize) throw new InSufficientException(option.value());
		if (values.size() > expectedSize) throw new TooManyArgumentsException(option.value());
		return Optional.of(values);
	}


	private static List<String> values(List<String> arguments, int index) {
		return arguments.subList(index + 1, IntStream.range(index + 1, arguments.size())
			.filter(it -> arguments.get(it).contains("-"))
			.findFirst().orElse(arguments.size()));
	}

}
