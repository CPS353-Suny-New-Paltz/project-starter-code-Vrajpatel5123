package numberlettercountdatastoring;

import java.util.ArrayList;
import java.util.List;
import numberlettercountcomputing.ComputingApi;
import numberlettercountcomputing.PassData;

public class DataStoreApiImpl implements DataStoreApi {
	private ComputingApi computingApi;
	private List<Integer> storedNumbers = new ArrayList<>();

	public DataStoreApiImpl() {}

	public DataStoreApiImpl(ComputingApi computingApi) {
		this.computingApi = computingApi;
	}

	public void setComputingApi(ComputingApi computingApi) {
		this.computingApi = computingApi;
	}

	public int insertRequest(DataRequest dataRequest) {
		if (dataRequest == null) {
			return -1;
		}

		System.out.println("Processing data request: " + dataRequest);

		try {
			String[] numberStrings = dataRequest.getDataContent().split(",");
			for (String numStr : numberStrings) {
				int number = Integer.parseInt(numStr.trim());
				storedNumbers.add(number);

				if (computingApi != null) {
					PassData passData = computingApi.passData(number);
					System.out.println("Created PassData for number " + number + ": " + passData);

					List<Integer> processedResults = computingApi.processPassData(passData);
					System.out.println("Processed results: " + processedResults);
				}
			}
			return 0;
		} catch (Exception e) {
			return -1;
		}
	}

	public boolean validateNumber(int number) {
		boolean basicValidation = number >= 0;
		if (computingApi != null) {
			try {
				PassData validationPassData = computingApi.passData(number);
				List<Integer> validationResults = computingApi.processPassData(validationPassData);
				return basicValidation && validationResults != null && !validationResults.isEmpty();
			} catch (Exception e) {
				return false;
			}
		}
		return basicValidation;
	}

	public List<Integer> fetchAllData() {
		return new ArrayList<>(storedNumbers);
	}

	public int getStoredDataCount() {
		return storedNumbers.size();
	}
}