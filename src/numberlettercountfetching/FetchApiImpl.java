package numberlettercountfetching;

import java.util.Arrays;
import java.util.List;

import numberlettercountdatastoring.DataStoreApi;

public class FetchApiImpl implements FetchApi {
	private DataStoreApi dataStoreApi;

	@Override
	public List<Integer> insertRequest(FetchRequest fetchRequest) {
		return Arrays.asList(-1); // failure value
	}

	@Override
	public InputSource getInputSource() {
		return null;
	}

	@Override
	public OutputResult getOutputResult() {
		return null;
	}

	@Override
	public Delimiters getDelimiters() {
		return null;
	}

	@Override
	public PassData inputSource() {
		return null;
	}

	@Override
	public Display displayIt() {
		return null;
	}
}