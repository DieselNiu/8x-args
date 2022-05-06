import exception.InSufficientException;
import exception.TooManyArgumentException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class SingleValuedParserTest {
	@Test // sad path
	public void should_not_accept_extra_argument_for_single_valued_parser() {
		TooManyArgumentException e = Assertions.assertThrows(TooManyArgumentException.class,
			() -> new SingleValuedOptionParser<>(0, Integer::parseInt).parse(Arrays.asList("-p", "8080", "8081"), BoolOptionParserTest.option("p")));
		assertEquals("p", e.getOption());
	}


	@ParameterizedTest // sad path
	@ValueSource(strings = {"-p -l", "-p"})
	public void should_not_accept_insufficient_argument_for_single_valued_parser(String value) {
		InSufficientException e = Assertions.assertThrows(InSufficientException.class,
			() -> new SingleValuedOptionParser<>(0, Integer::parseInt).parse(Arrays.asList(value.split(" ")), BoolOptionParserTest.option("p")));
		assertEquals("p", e.getOption());
	}

	@Test //default value
	public void should_set_default_value_to_0_for_int_option() {
		Object whatever = new Object();
		Function<String, Object> parsed = (it) -> null;
		assertSame(whatever, new SingleValuedOptionParser<>(whatever, parsed).parse(Arrays.asList(), BoolOptionParserTest.option("p")));
	}

	@Test // happy path
	public void should_parse_int_option() {
		Object whatever = new Object();
		Object parsed = new Object();
		Function<String, Object> parse = (it) -> parsed;

		assertSame(parsed, new SingleValuedOptionParser<>(whatever, parse).parse(Arrays.asList("-p", "8080"), BoolOptionParserTest.option("p")));
	}


}
