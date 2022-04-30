package com.github.dieselniu;

import com.github.dieselniu.exception.IllegalOptionException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class ArgsTest {


	//Multi Options
	@Test
	public void should_example_1() {
		MultiOptions multiOptions = Args.parse(MultiOptions.class, "-l", "-p", "8080", "-d", "/usr/log");
		assertTrue(multiOptions.logging);
		assertEquals(8080, multiOptions.port());
		assertEquals("/usr/log", multiOptions.directory());
	}

	record MultiOptions(@Option("l") boolean logging, @Option("p") int port, @Option("d") String directory) {

	}


	@Test
	public void should_example_2() {
		ListOptions options = Args.parse(ListOptions.class, "-g", "this", "is", "a", "list", "-d", "1", "2", "-3", "5");
		assertArrayEquals(new String[]{"this", "is", "a", "list"}, options.group());
		assertArrayEquals(new Integer[]{1, 2, -3, 5}, options.decimals());
	}


	record ListOptions(@Option("g") String[] group, @Option("d") Integer[] decimals) {
	}


	@Test
	public void should_throw_illegal_option_exception_if_annotation_not_present() {
		IllegalOptionException e = assertThrows(IllegalOptionException.class, () -> Args.parse(OptionsWithoutAnnotation.class, "-l", "-p", "8080", "-d", "/usr/log"));
		assertEquals("port", e.getParameter());
	}

	record OptionsWithoutAnnotation(@Option("l") boolean logging, int port, @Option("d") String directory) {

	}
}
