import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Args {
	public static <T> T parse(Class<T> optionsClass, String... args) {
		try {
			List<String> arguments = Arrays.asList(args);
			Constructor<?> constructor = optionsClass.getDeclaredConstructors()[0];
			Object[] values = Arrays.stream(constructor.getParameters()).map(it -> parseValue(arguments, it)).toArray();
			return (T) constructor.newInstance(values);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static Object parseValue(List<String> arguments, Parameter parameter) {
		return PARSER.get(parameter.getType()).parse(arguments, parameter.getDeclaredAnnotation(Option.class));
	}


	private static Map<Class<?>, OptionParser> PARSER = Map.of(boolean.class, new BoolOptionParser(), int.class,
		new SingleValuedOptionParser<>(0, Integer::parseInt),
		String.class, new SingleValuedOptionParser<>("", String::valueOf));


}
