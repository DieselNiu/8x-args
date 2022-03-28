import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SingleValuedOptionParserTest {
	@Test
	public void should_not_accept_extra_argument_for_single_valued_option() {
		TooManyArgumentsException e = Assertions.assertThrows(TooManyArgumentsException.class, () -> new SingleValueOptionParser<Integer>(0, Integer::parseInt).parse(Arrays.asList("-p", "8080", "8081"), BooleanOptionParserTest.option("p")));
		assertThat(e.getOption()).isEqualTo("p");

	}


	@ParameterizedTest
	@ValueSource(strings = {"-p -l", "-p"})
	public void should_not_accept_insufficient_argument_for_single_valued_option(String arguments) {
		InsufficientArgumentsException e = Assertions.assertThrows(InsufficientArgumentsException.class, () -> new SingleValueOptionParser<Integer>(0, Integer::parseInt).parse(Arrays.asList(arguments.split(" ")), BooleanOptionParserTest.option("p")));
		assertThat(e.getOption()).isEqualTo("p");
	}

	@Test
	public void should_set_default_value_to_0_for_int_option() {
		assertEquals(0, new SingleValueOptionParser<Integer>(0, Integer::parseInt).parse(List.of(), BooleanOptionParserTest.option("p")));
	}

	@Test
	public void should_not_accept_extra_argument_for_string_single_valued_option() {
		TooManyArgumentsException e = Assertions.assertThrows(TooManyArgumentsException.class, () -> new SingleValueOptionParser<>("", String::valueOf).parse(Arrays.asList("-d", "/usr/local", "/usr/vars"), BooleanOptionParserTest.option("d")));
		assertThat(e.getOption()).isEqualTo("d");
	}


}
