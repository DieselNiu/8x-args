package com.github.dieselniu;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;

import static com.github.dieselniu.BooleanOptionParserTest.option;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SingleValuedOptionParserTest {
	@Test
	public void should_not_accept_extra_argument_for_single_valued_option() {
		TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class, () -> new SingleValuedOptionParser<Integer>(0, Integer::parseInt).parse(Arrays.asList("-p", "8080", "8081"), option("p")));
		assertEquals("p", e.getOption());
	}

	@ParameterizedTest
	@ValueSource(strings = {"-p -l", "-p"})
	public void should_not_accept_insufficient_argument_for_single_valued_option(String argument) {
		InSufficientException e = assertThrows(InSufficientException.class, () -> new SingleValuedOptionParser<Integer>(0, Integer::parseInt).parse(Arrays.asList(argument.split(" ")), option("p")));
		assertEquals("p", e.getOption());
	}

	@Test
	public void should_set_default_to_0_for_int_option() {
		assertEquals(0, new SingleValuedOptionParser<Integer>(0, Integer::parseInt).parse(Arrays.asList(), option("p")));
	}
}
