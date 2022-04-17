package com.github.dieselniu;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

public class Args {
	public static <T> T parse(Class<T> optionClass, String... args) {
		List<String> arguments = Arrays.asList(args);
		try {
			Constructor<?> constructor = optionClass.getDeclaredConstructors()[0];
			Parameter parameter = constructor.getParameters()[0];
			Option option = parameter.getDeclaredAnnotation(Option.class);

			Object value = null;

			if (parameter.getType() == boolean.class) {
				value = arguments.contains("-" + option.value());
			}

			if (parameter.getType() == int.class) {
				int index = arguments.indexOf("-" + option.value());
				value = Integer.parseInt(arguments.get(index + 1));
			}

			if (parameter.getType() == String.class) {
				int index = arguments.indexOf("-" + option.value());
				value = arguments.get(index + 1);
			}
			return (T) constructor.newInstance(value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
