import exception.InSufficientException;
import exception.TooManyArgumentException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OptionParsersTest {

	@Nested
	class UnaryOptionParser {

		@Test // sad path
		public void should_not_accept_extra_argument_for_single_valued_parser() {
			TooManyArgumentException e = Assertions.assertThrows(TooManyArgumentException.class,
				() -> OptionParsers.unary(0, Integer::parseInt).parse(Arrays.asList("-p", "8080", "8081"), BoolOptionParser.option("p")));
			assertEquals("p", e.getOption());
		}


		@ParameterizedTest // sad path
		@ValueSource(strings = {"-p -l", "-p"})
		public void should_not_accept_insufficient_argument_for_single_valued_parser(String value) {
			InSufficientException e = Assertions.assertThrows(InSufficientException.class,
				() -> OptionParsers.unary(0, Integer::parseInt).parse(Arrays.asList(value.split(" ")), BoolOptionParser.option("p")));
			assertEquals("p", e.getOption());
		}

		@Test //default value
		public void should_set_default_value_to_0_for_int_option() {
			Object whatever = new Object();
			Function<String, Object> parsed = (it) -> null;
			assertSame(whatever, OptionParsers.unary(whatever, parsed).parse(Arrays.asList(), BoolOptionParser.option("p")));
		}

		@Test // happy path
		public void should_parse_int_option() {
			Object whatever = new Object();
			Object parsed = new Object();
			Function<String, Object> parse = (it) -> parsed;

			assertSame(parsed, OptionParsers.unary(whatever, parse).parse(Arrays.asList("-p", "8080"), BoolOptionParser.option("p")));
		}
	}

	@Nested
	class BoolOptionParser {


		@Test //sad path
		public void should_not_accept_extra_argument_for_bool_option() {
			TooManyArgumentException e = Assertions.assertThrows(TooManyArgumentException.class,
				() -> OptionParsers.bool().parse(Arrays.asList("-l", "/"), option("l")));
			assertEquals("l", e.getOption());
		}

		@Test // default value
		public void should_set_bool_to_false_if_flag_not_present() {
			assertFalse(OptionParsers.bool().parse(List.of(), option("l")));
		}

		@Test // happy path
		public void should_set_bool_to_true_if_flag_present() {
			assertTrue(OptionParsers.bool().parse(List.of("-l"), option("l")));
		}

		static Option option(String argument) {
			return new Option() {

				@Override
				public Class<? extends Annotation> annotationType() {
					return Option.class;
				}

				@Override
				public String value() {
					return argument;
				}
			};
		}
	}


}
