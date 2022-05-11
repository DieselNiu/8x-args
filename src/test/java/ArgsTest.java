import exception.IllegalOptionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArgsTest {


	//Multi Options
	//TODO -l -p 8080 -d /usr/log
	@Test
	public void should_parse_multi_options() {
		Options options = Args.parse(Options.class, "-l", "-p", "8080", "-d", "/usr/log");
		assertTrue(options.logging());
		assertEquals(8080, options.port());
		assertEquals("/usr/log", options.directory());
	}


	@Test
	public void should_throw_illegal_option_if_option_not_found() {
		IllegalOptionException e = Assertions.assertThrows(IllegalOptionException.class,
			() -> Args.parse(OptionsWithOutAnnotation.class, "-l", "-p", "8080", "-d", "/usr/log"));
		assertEquals("port", e.getParameter());
	}

	record OptionsWithOutAnnotation(@Option("l") boolean logging, int port, @Option("d") String directory) {

	}


	@Test
	public void should_1() {
		ListOptions listOptions = Args.parse(ListOptions.class, "-g", "this", "is", "a", "list", "-d", "1", "2", "-3", "5");
		assertArrayEquals(listOptions.groups(), new String[]{"this", "is", "a", "list"});
		assertArrayEquals(listOptions.decimals(), new Integer[]{1, 2, -3, 5});
	}

	record Options(@Option("l") boolean logging, @Option("p") int port, @Option("d") String directory) {

	}

	record ListOptions(@Option("g") String[] groups, @Option("d") Integer[] decimals) {

	}
}
