package com.github.dieselniu;

import java.util.List;
import java.util.function.Function;

class SingleValuedOptionParser<T> implements OptionParser<T> {
	Function<String, T> valueParser;
	T defaultValue;


	public SingleValuedOptionParser(T defaultValue, Function<String, T> valueParser) {
		this.defaultValue = defaultValue;
		this.valueParser = valueParser;
	}

	@Override
	public T parse(List<String> arguments, Option option) {
		int index = arguments.indexOf("-" + option.value());
		if (index == -1) return defaultValue;
		if (index + 1 == arguments.size() ||
			arguments.get(index + 1).startsWith("-")) throw new InSufficientException(option.value());
		if (index + 2 < arguments.size() &&
			!arguments.get(index + 2).startsWith("-")) throw new TooManyArgumentsException(option.value());
		return valueParser.apply(arguments.get(index + 1));
	}

}