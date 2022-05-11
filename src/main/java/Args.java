import exception.IllegalOptionException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Args {
	public static <T> T parse(Class<T> optionsClass, String... args) {
		try {
			List<String> arguments = Arrays.asList(args);
			Constructor<?> constructor = optionsClass.getDeclaredConstructors()[0];
			Object[] values = Arrays.stream(constructor.getParameters()).map(it -> parseValue(arguments, it)).toArray();
			return (T) constructor.newInstance(values);
		} catch (IllegalOptionException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static Object parseValue(List<String> arguments, Parameter parameter) {
		if (!parameter.isAnnotationPresent(Option.class)) throw new IllegalOptionException(parameter.getName());
		return PARSER.get(parameter.getType()).parse(arguments, parameter.getDeclaredAnnotation(Option.class));
	}


	private static Map<Class<?>, OptionParser> PARSER = Map.of(boolean.class, SingleValuedOptionParser.bool(), int.class,
		SingleValuedOptionParser.unary(0, Integer::parseInt),
		String.class, SingleValuedOptionParser.unary("", String::valueOf));


}
