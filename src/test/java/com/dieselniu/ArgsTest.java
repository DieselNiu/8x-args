package com.dieselniu;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ArgsTest {

	// -l -p 8080 -d /usr/local

	// single option
	//  TODO-1  boolean -l

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

	static record BooleanOption(@Option("l") boolean logging) {

	}
	//  TODO-2  int -p 8080

	@Test
	public void should_parse_int_as_option_value() {
		IntOption intOption = Args.parse(IntOption.class, "-p", "8080");
		assertThat(intOption.port()).isEqualTo(8080);
	}

	static record IntOption(@Option("p") int port) {

	}

	//  TODO-3  String -d /usr/local
	static record StringOption(@Option("d") String directory) {

	}

	@Test
	public void should_get_string_as_option_value() {
		StringOption stringOption = Args.parse(StringOption.class, "-d", "/usr/local");
		assertThat(stringOption.directory()).isEqualTo("/usr/local");
	}


	// multiple option: TODO-4 	-l -p 8080 -d /usr/local

	// sad path:
	// TODO-5 boolean: -l t ; -l t f
	// TODO-6 int : -p / -p 8080
	// TODO-7 string : -d /usr/logs /usr/xxx

	// default value
	// TODO-8 -bool : false
	// TODO-9 -string : "
	// TODO-10 -int : 0

	@Test
	@Disabled
	public void should() {
		Options options = Args.parse(Options.class, "-l -p:8080,-d:/usr/local");
		assertThat(options.port()).isEqualTo(8080);
		assertThat(options.logging()).isEqualTo(Boolean.TRUE);
		assertThat(options.directory()).isEqualTo("/usr/local");
	}

	static record Options(@Option("l") boolean logging, @Option("p") int port, @Option("d") String directory) {
	}


	// -g this is a list -d 1 2 -3 5
	@Test
	@Disabled
	public void should2() {
		ListOptions listOptions = Args.parse(ListOptions.class, "-g", "this", "is", "a", "list", "-d", "1", "2", "-3", "5");
		String[] group = listOptions.group();
		assertThat(new String[]{"this", "is", "a", "list"}).isEqualTo(group);
		assertThat(new int[]{1, 2, -3, 5}).isEqualTo(listOptions.decimals());

	}


	static record ListOptions(@Option("g") String[] group, @Option("d") int[] decimals) {}

}

