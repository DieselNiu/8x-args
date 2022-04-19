package com.github.dieselniu;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static com.github.dieselniu.BooleanOptionParserTest.option;
import static org.junit.jupiter.api.Assertions.*;

public class SingleValuedOptionParserTest {
	@Test // Sad Path
	public void should_not_accept_extra_argument_for_single_valued_option() {
		TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class, () -> new SingleValuedOptionParser<>(0, Integer::parseInt).parse(Arrays.asList("-p", "8080", "8081"), option("p")));
		assertEquals("p", e.getOption());
	}

	@ParameterizedTest  // Sad Path
	@ValueSource(strings = {"-p -l", "-p"})
	public void should_not_accept_insufficient_argument_for_single_valued_option(String argument) {
		InSufficientException e = assertThrows(InSufficientException.class, () -> new SingleValuedOptionParser<>(0, Integer::parseInt).parse(Arrays.asList(argument.split(" ")), option("p")));
		assertEquals("p", e.getOption());
	}

	@Test // Default value
	public void should_set_default_to_0_for_int_option() {
		Function<String, Object> whatever = (it) -> null;
		Object defaultValue = new Object();
		assertSame(defaultValue, new SingleValuedOptionParser<>(defaultValue, whatever).parse(List.of(), option("p")));
	}

	@Test // Happy Path
	public void should_parse_value_if_flag_present() {
		Object parsed = new Object();
		Function<String, Object> parse = (it) -> parsed;
		Object whatever = new Object();
		assertSame(parsed, new SingleValuedOptionParser<>(whatever, parse).parse(Arrays.asList("-p", "8080"), option("p")));
	}
}
