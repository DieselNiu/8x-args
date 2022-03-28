import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ArgsTest {

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
