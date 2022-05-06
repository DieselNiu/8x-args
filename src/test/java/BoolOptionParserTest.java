import exception.TooManyArgumentException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BoolOptionParserTest {

	//Sad Path
	//-int  -p 8080 8081
	//-string -d /usr/log /usr/xxx
	//Default value
	// -bool false
	// -int 0
	// -string ""

	@Test //sad path
	public void should_not_accept_extra_argument_for_bool_option() {
		TooManyArgumentException e = Assertions.assertThrows(TooManyArgumentException.class,
			() -> new BoolOptionParser().parse(Arrays.asList("-l", "/"), option("l")));
		assertEquals("l", e.getOption());
	}

	@Test // default value
	public void should_set_bool_to_false_if_flag_not_present() {
		assertFalse(new BoolOptionParser().parse(List.of(), option("l")));
	}

	@Test // happy path
	public void should_set_bool_to_true_if_flag_present() {
		assertTrue(new BoolOptionParser().parse(List.of("-l"), option("l")));
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
