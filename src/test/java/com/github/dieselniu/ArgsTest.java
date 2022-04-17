package com.github.dieselniu;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class ArgsTest {
	// Single Option:
	@Test
	public void should_set_boolean_option_to_true_when_flag_present() {
		BooleanOption booleanOption = Args.parse(BooleanOption.class, "-l");
		assertTrue(booleanOption.logging());
	}

	@Test
	public void should_set_boolean_option_to_false_when_flag_not_present() {
		BooleanOption booleanOption = Args.parse(BooleanOption.class, "");
		assertFalse(booleanOption.logging());
	}

	record BooleanOption(@Option("l") boolean logging) {}

	@Test
	public void should_parse_int_as_option_value() {
		IntOption intOption = Args.parse(IntOption.class, "-p", "8080");
		assertEquals(8080, intOption.port());
	}

	record IntOption(@Option("p") int port) {}

	@Test
	public void should_get_string_as_option_value() {
		StringOption stringOption = Args.parse(StringOption.class, "-d", "/usr/log");
		assertThat(stringOption.logging()).isEqualTo("/usr/log");
	}

	record StringOption(@Option("d") String logging) {}


	//Multi Options
	//TODO  -l -p 8080 -d /usr/log
	//Sad Path
	//TODO -Bool -l tf
	//TODO -Integer -p
	//TODO -String -d /usr/log /usr/local
	//Default value
	//TODO -Bool false
	//TODO -Integer 0
	//TODO -String ""


	@Test
	@Disabled
	public void should_example_1() {
		Options options = Args.parse(Options.class, "-l", "-p", "8080", "-d", "/usr/log");
		assertTrue(options.logging);
		assertEquals(8080, options.port());
		assertEquals("/usr/log", options.directory());
	}

	@Test
	@Disabled
	public void should_example_2() {
		ListOptions listOptions = Args.parse(ListOptions.class, "-g", "this", "is", "a", "list", "-d", "1", "2", "-3", "5");
		assertArrayEquals(new String[]{"this", "is", "a", "list"}, listOptions.group());
		assertArrayEquals(new int[]{1, 2, -3, 5}, listOptions.decimals());
	}

	static record Options(@Option("l") boolean logging, @Option("p") int port, @Option("d") String directory) {

	}

	static record ListOptions(String[] group, int[] decimals) {
	}
}
