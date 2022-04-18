package com.github.dieselniu;

import java.util.List;
import java.util.function.Function;

class SingleValuedOptionParser<T> implements OptionParser<T> {
	Function<String, T> valueParser;


	public SingleValuedOptionParser(Function<String, T> valueParser) {
		this.valueParser = valueParser;
	}

	@Override
	public T parse(List<String> arguments, Option option) {
		return valueParser.apply(arguments.get(arguments.indexOf("-" + option.value()) + 1));
	}

}
