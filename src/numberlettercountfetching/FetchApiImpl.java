package numberlettercountfetching;

import java.util.ArrayList;
import java.util.List;
import numberlettercountdatastoring.DataStoreApi;

public class FetchApiImpl implements FetchApi {
	private DataStoreApi dataStoreApi;
	private List<Integer> storedData = new ArrayList<>();

	public void setDataStoreApi(DataStoreApi dataStoreApi) {
		this.dataStoreApi = dataStoreApi;
	}

	public List<Integer> insertRequest(FetchRequest fetchRequest) {
		if (fetchRequest != null && fetchRequest.getData() != null) {
			storedData.addAll(fetchRequest.getData());

			// Validate each number if DataStoreApi is available
			if (dataStoreApi != null) {
				for (Integer number : fetchRequest.getData()) {
					boolean isValid = dataStoreApi.validateNumber(number);
					System.out.println("Number " + number + " validation: " + isValid);
				}
			}

			return new ArrayList<>(fetchRequest.getData());
		}
		return List.of(-1);
	}

	public boolean validateNumber(int number) {
		boolean basicValidation = number >= 0;

		// If DataStoreApi is available, use it for additional validation
		if (dataStoreApi != null) {
			return basicValidation && dataStoreApi.validateNumber(number);
		}

		return basicValidation;
	}

	public List<Integer> getStoredData() {
		return new ArrayList<>(storedData);
	}

	public int getStoredDataCount() {
		return storedData.size();
	}
}