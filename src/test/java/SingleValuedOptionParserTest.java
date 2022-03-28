import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertSame;

public class SingleValuedOptionParserTest {
	@Test// sad path
	public void should_not_accept_extra_argument_for_single_valued_option() {
		TooManyArgumentsException e = Assertions.assertThrows(TooManyArgumentsException.class, () -> new SingleValueOptionParser<>(0, Integer::parseInt).parse(Arrays.asList("-p", "8080", "8081"), BooleanOptionParserTest.option("p")));
		assertThat(e.getOption()).isEqualTo("p");

	}


	@ParameterizedTest
	@ValueSource(strings = {"-p -l", "-p"}) // sad path
	public void should_not_accept_insufficient_argument_for_single_valued_option(String arguments) {
		InsufficientArgumentsException e = Assertions.assertThrows(InsufficientArgumentsException.class, () -> new SingleValueOptionParser<Integer>(0, Integer::parseInt).parse(Arrays.asList(arguments.split(" ")), BooleanOptionParserTest.option("p")));
		assertThat(e.getOption()).isEqualTo("p");
	}

	@Test // default value
	public void should_set_default_value_to_0_for_int_option() {
		Function<String, Object> whatever = (it) -> null;
		Object defaultValue = new Object();
		assertSame(defaultValue, new SingleValueOptionParser<>(defaultValue, whatever).parse(List.of(), BooleanOptionParserTest.option("p")));
	}

	@Test// happy path
	public void should_parse_value_if_flag_present() {
		Object parsed = new Object();
		Function<String, Object> parse = (it) -> parsed;
		Object whatever = new Object();
		assertSame(parsed, new SingleValueOptionParser<>(whatever, parse).parse(List.of("-p", "8080"), BooleanOptionParserTest.option("p")));
	}

	@Test // sad path
	public void should_not_accept_extra_argument_for_string_single_valued_option() {
		TooManyArgumentsException e = Assertions.assertThrows(TooManyArgumentsException.class, () -> new SingleValueOptionParser<>("", String::valueOf).parse(Arrays.asList("-d", "/usr/local", "/usr/vars"), BooleanOptionParserTest.option("d")));
		assertThat(e.getOption()).isEqualTo("d");
	}
}
