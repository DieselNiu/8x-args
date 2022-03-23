import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ArgsTest {
	// -l -p 8080 -d /usr/local
	// [l] [-p,8080] [-d,/usr/local]
	//single option
	//  TODO boolean -l
	@Test
	public void should_set_boolean_option_to_true_if_flag_present() {
		BooleanOption booleanOption = Args.parse(BooleanOption.class, "-l");
		assertThat(booleanOption.logging()).isEqualTo(Boolean.TRUE);
	}

	@Test
	public void should_set_boolean_option_to_false_if_flag_not_present() {
		BooleanOption booleanOption = Args.parse(BooleanOption.class);
		assertThat(booleanOption.logging()).isEqualTo(Boolean.FALSE);
	}

	static record BooleanOption(@Option("l") boolean logging) {}
	//  TODO  int   -p 8080

	@Test
	public void should_parse_int_as_option_value() {
		IntOption intOption = Args.parse(IntOption.class, "-p", "8080");
		assertThat(intOption.port()).isEqualTo(8080);
	}

	static record IntOption(@Option("p") int port) {}
	//  TODO string -d /usr/local

	@Test
	public void should_parse_string_as_option_value() {
		StringOption stringOption = Args.parse(StringOption.class, "-d", "usr/local");
		assertThat(stringOption.directory()).isEqualTo("usr/local");
	}

	static record StringOption(@Option("d") String directory) {}
	// multi options
	// TODO -l -p 8080 -d /usr/local

	@Test
	public void should_parse_multi_option() {
		MultiOptions multiOptions = Args.parse(MultiOptions.class, "-l", "-p", "8080", "-d", "usr/local");
		assertThat(multiOptions.directory()).isEqualTo("usr/local");
		assertThat(multiOptions.logging()).isEqualTo(Boolean.TRUE);
		assertThat(multiOptions.port()).isEqualTo(8080);
	}

	static record MultiOptions(@Option("l") boolean logging, @Option("p") int port, @Option("d") String directory) {}
	// side path
	// TODO boolean -l x

	// TODO int     -p 8080 9090
	// TODO string  -d /usr/local /usr/xxx
	// default value
	// TODO boolean -l true

	// TODO int    -p  0

	// TODO string -d /


}
