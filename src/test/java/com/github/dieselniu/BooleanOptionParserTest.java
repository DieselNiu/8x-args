package com.github.dieselniu;

import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class BooleanOptionParserTest {
	@Test // Happy Path
	public void should_set_value_to_true_if_option_present() {
		assertTrue(new BooleanOptionParser().parse(List.of("-l"), option("l")));
	}

	@Test // Sad Path
	public void should_not_accept_extra_argument_for_boolean_option() {
		TooManyArgumentsException exception = assertThrows(TooManyArgumentsException.class, () ->
			new BooleanOptionParser().parse(Arrays.asList("-l", "t"), option("l")));
		assertThat(exception.getOption()).isEqualTo("l");
	}


	@Test  // Default value
	public void should_set_default_value_to_false_if_option_not_present() {
		assertFalse(new BooleanOptionParser().parse(List.of(), option("l")));
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
