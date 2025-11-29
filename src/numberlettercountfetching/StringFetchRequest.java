package numberlettercountfetching;

import java.util.List;
import java.util.stream.Collectors;

public class StringFetchRequest extends FetchRequest {
	private String text;

	public StringFetchRequest(String text) {
		super(convertStringToNumbers(text));
		this.text = text;
	}

	private static List<Integer> convertStringToNumbers(String text) {
		return text.chars()
				.boxed()
				.collect(Collectors.toList());
	}

	public String getStringData() {
		return text;
	}
}