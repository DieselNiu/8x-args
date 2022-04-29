package com.github.dieselniu;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static com.github.dieselniu.BooleanOptionParserTest.BooleanOptionParser.option;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class BooleanOptionParserTest {
	@Nested
	class BooleanOptionParser {
		@Test // Happy Path
		public void should_set_value_to_true_if_option_present() {
			assertTrue(OptionParsers.bool().parse(List.of("-l"), option("l")));
		}

		@Test // Sad Path
		public void should_not_accept_extra_argument_for_boolean_option() {
			TooManyArgumentsException exception = assertThrows(TooManyArgumentsException.class, () ->
				OptionParsers.bool().parse(Arrays.asList("-l", "t"), option("l")));
			assertThat(exception.getOption()).isEqualTo("l");
		}


		@Test  // Default value
		public void should_set_default_value_to_false_if_option_not_present() {
			assertFalse(OptionParsers.bool().parse(List.of(), option("l")));
		}


		static Option option(String value) {
			return new Option() {
				@Override
				public Class<? extends Annotation> annotationType() {
					return Option.class;
				}

				@Override
				public String value() {
					return value;
				}
			};
		}
	}

	@Nested
	class SingleValuedOptionParser {
		@Test // Sad Path
		public void should_not_accept_extra_argument_for_single_valued_option() {
			TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class, () -> OptionParsers.unary(0, Integer::parseInt).parse(Arrays.asList("-p", "8080", "8081"), option("p")));
			assertEquals("p", e.getOption());
		}

		@ParameterizedTest  // Sad Path
		@ValueSource(strings = {"-p -l", "-p"})
		public void should_not_accept_insufficient_argument_for_single_valued_option(String argument) {
			InSufficientException e = assertThrows(InSufficientException.class, () -> OptionParsers.unary(0, Integer::parseInt).parse(Arrays.asList(argument.split(" ")), option("p")));
			assertEquals("p", e.getOption());
		}

		@Test // Default value
		public void should_set_default_to_0_for_int_option() {
			Function<String, Object> whatever = (it) -> null;
			Object defaultValue = new Object();
			assertSame(defaultValue, OptionParsers.unary(defaultValue, whatever).parse(List.of(), option("p")));
		}

		@Test // Happy Path
		public void should_parse_value_if_flag_present() {
			Object parsed = new Object();
			Function<String, Object> parse = (it) -> parsed;
			Object whatever = new Object();
			assertSame(parsed, OptionParsers.unary(whatever, parse).parse(Arrays.asList("-p", "8080"), option("p")));
		}
	}

}
