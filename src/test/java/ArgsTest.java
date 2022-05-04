import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArgsTest {


	@Test
	@Disabled
	public void should_() {
		Options options = Args.parse(Options.class, "-l", "-p", "8080", "-d", "/usr/log");
		assertTrue(options.logging());
		assertEquals(8080, options.port());
		assertEquals("/usr/log", options.directory());
	}

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
