import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArgsTest {
	// Single Option
	// -bool -l
	// -int -p 8080
	// -string -d /usr/log
	// Multi Options
	// -l -p 8080 -d /usr/log
	// Sad Path
	// -bool -l /
	// -int -p 8080 8081
	// -string -d /usr/log /usr/xxx
	// Default value
	// -bool -l false
	// -int  -p 0
	// -string ""


	@Test
	@Disabled
	public void should_() {
		Options options = Args.parse(Options.class, "-l", "-p", "8080", "-d", "/usr/log");
		assertTrue(options.logging());
		assertEquals(8080, options.port());
		assertEquals("/usr/log", options.directory());
	}

	record Options(@Option("l") Boolean logging, @Option("p") int port, @Option("d") String directory) {

	}

	@Test
	@Disabled
	public void should_1() {
		ListOptions options = Args.parse(ListOptions.class, "-g", "this", "is", "a", "list", "-d", "1", "2", "-3", "5");
		assertArrayEquals(new String[]{"this", "is", "a", "list"}, options.groups());
		assertArrayEquals(new int[]{1, 2, -3, 5}, options.decimals());
	}

	record ListOptions(@Option("g") String[] groups, @Option("d") int[] decimals) {

	}
}
