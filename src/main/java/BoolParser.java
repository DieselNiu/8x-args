import java.util.List;

class BoolParser implements OptionParser {

	@Override
	public Object parse(List<String> arguments, Option option) {
		return arguments.contains("-" + option.value());
	}
}
