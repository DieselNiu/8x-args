import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BooleanOptionParserTest {
	// sad path:
	//  TODO:  -bool -l t / -l t f
	//  TODO: -int -p /  -p  8080 8081
	//  TODO: - string -d /  -d /usr/logs /usr/vars
	// dafault value:
	//  TODO:  -bool : false
	// TODO:  -int : 0
	// TODO: -string ""


	@Test
	public void should_not_accept_extra_argument_for_boolean_option() {
		TooManyArgumentsException tooManyArgumentsException = assertThrows(TooManyArgumentsException.class,
			() -> new BooleanParser().parse(Arrays.asList("-l", "t"), option("l")));
		assertThat(tooManyArgumentsException.getOption()).isEqualTo("l");

	}

	@Test
	public void should_set_default_value_to_false_if_option_not_present() {
		assertFalse(new BooleanParser().parse(List.of(), option("l")));
	}


	public static Option option(String value) {
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
