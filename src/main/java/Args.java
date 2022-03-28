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
			Object[] values = Arrays.stream(constructor.getParameters()).map(it -> parseOption(arguments, it)).toArray();
			return (T) constructor.newInstance(values);
		} catch (IllegalOptionException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static Object parseOption(List<String> arguments, Parameter parameter) {
		if (!parameter.isAnnotationPresent(Option.class)) throw new IllegalOptionException(parameter.getName());
		Option option = parameter.getAnnotation(Option.class);
		Class<?> type = parameter.getType();
		OptionParser parser = PARSER.get(type);
		return parser.parse(arguments, option);
	}


	private static Map<Class<?>, OptionParser> PARSER = Map.of(boolean.class, new BooleanParser(),
		int.class, new SingleValueOptionParser<>(0, Integer::parseInt),
		String.class, new SingleValueOptionParser<>("", String::valueOf));
}
