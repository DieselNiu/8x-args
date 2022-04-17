package com.github.dieselniu;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Args {
	public static <T> T parse(Class<T> optionClass, String... args) {
		List<String> arguments = Arrays.asList(args);
		try {
			Constructor<?> constructor = optionClass.getDeclaredConstructors()[0];
			Object[] values = Arrays.stream(constructor.getParameters()).map(it -> parseOption(arguments, it)).toArray();

			return (T) constructor.newInstance(values);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static Object parseOption(List<String> arguments, Parameter parameter) {
		return PARSERS.get(parameter.getType()).parse(arguments, parameter.getDeclaredAnnotation(Option.class));
	}


	private static Map<Class<?>, OptionParser> PARSERS = Map.of(
		boolean.class, new BooleanParser(),
		int.class, new SingleValuedOptionParser<>(Integer::parseInt),
		String.class, new SingleValuedOptionParser<>(Function.identity()));


}
