package com.github.dieselniu;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class ArgsTest {


	//Multi Options
	//TODO  -l -p 8080 -d /usr/log
	@Test
	public void should_example_1() {
		MultiOptions multiOptions = Args.parse(MultiOptions.class, "-l", "-p", "8080", "-d", "/usr/log");
		assertTrue(multiOptions.logging);
		assertEquals(8080, multiOptions.port());
		assertEquals("/usr/log", multiOptions.directory());
	}

	record MultiOptions(@Option("l") boolean logging, @Option("p") int port, @Option("d") String directory) {

	}
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
	public void should_example_2() {
		ListOptions listOptions = Args.parse(ListOptions.class, "-g", "this", "is", "a", "list", "-d", "1", "2", "-3", "5");
		assertArrayEquals(new String[]{"this", "is", "a", "list"}, listOptions.group());
		assertArrayEquals(new int[]{1, 2, -3, 5}, listOptions.decimals());
	}


	static record ListOptions(String[] group, int[] decimals) {
	}
}
