package com.dieselniu;

import java.util.List;
import java.util.function.Function;

class SingleValuedParser<T> implements OptionParser {
	Function<String, T> valueParser;

	public SingleValuedParser(Function<String, T> valueParser) {
		this.valueParser = valueParser;
	}


	@Override
	public Object parse(List<String> arguments, Option option) {
		int index = arguments.indexOf("-" + option.value());
		String value = arguments.get(index + 1);
		return parseValue(value);
	}

	protected T parseValue(String value) {
		return valueParser.apply(value);
	}
}
