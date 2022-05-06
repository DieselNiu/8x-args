import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArgsTest {


	@Test
	public void should_parse_int_as_option_value() {
		IntOption intOption = Args.parse(IntOption.class, "-p", "8080");
		assertEquals(8080, intOption.port());
	}

	record IntOption(@Option("p") int port) {

	}

	@Test
	public void should_get_string_as_option_value() {
		StringOption stringOption = Args.parse(StringOption.class, "-d", "/usr/log");
		assertEquals("/usr/log", stringOption.directory());
	}

	record StringOption(@Option("d") String directory) {

	}

	//Multi Options
	//TODO -l -p 8080 -d /usr/log
	@Test
	public void should_parse_multi_options() {
		Options options = Args.parse(Options.class, "-l", "-p", "8080", "-d", "/usr/log");
		assertTrue(options.logging());
		assertEquals(8080, options.port());
		assertEquals("/usr/log", options.directory());
	}


	//Sad Path
	//-bool -l /
	//-int  -p 8080 8081
	//-string -d /usr/log /usr/xxx
	//Default value
	// -bool false
	// -int 0
	// -string ""


	@Test
	@Disabled
	public void should_1() {
		ListOptions listOptions = Args.parse(ListOptions.class, "-g", "this", "is", "a", "list", "-d", "1", "2", "-3", "5");
		assertArrayEquals(listOptions.groups(), new String[]{"this", "is", "a", "list"});
		assertArrayEquals(listOptions.decimals(), new int[]{1, 2, -3, 5});
	}

	record Options(@Option("l") boolean logging, @Option("p") int port, @Option("d") String directory) {

	}

	record ListOptions(@Option("g") String[] groups, @Option("d") int[] decimals) {

	}
}
