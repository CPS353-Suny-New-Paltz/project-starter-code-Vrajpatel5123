// File: DataStoreApiImpl.java (FIXED - uses only available ComputingApi methods)
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
			return -1; // failure
		}

		System.out.println("Processing data request: " + dataRequest);

		// Extract numbers from data request and store them
		try {
			String[] numberStrings = dataRequest.getDataContent().split(",");
			for (String numStr : numberStrings) {
				int number = Integer.parseInt(numStr.trim());
				storedNumbers.add(number);

				// USE computingApi: Process the number using available methods
				if (computingApi != null) {
					PassData passData = computingApi.passData(number);
					System.out.println("Created PassData for number " + number + ": " + passData);

					List<Integer> processedResults = computingApi.processPassData(passData);
					System.out.println("Processed results: " + processedResults);
				}
			}
			return 0; // success
		} catch (Exception e) {
			return -1; // failure
		}
	}


	public List<Integer> fetchAllData() {
		// USE computingApi: Process the stored numbers before returning
		if (computingApi != null && !storedNumbers.isEmpty()) {
			System.out.println("Processing stored data with ComputingApi");

			// Create a PassData object with all stored numbers
			PassData bulkPassData = new PassData();
			StringBuilder dataBuilder = new StringBuilder();
			for (Integer num : storedNumbers) {
				if (dataBuilder.length() > 0) {
					dataBuilder.append(",");
				}
				dataBuilder.append(num);
			}
			bulkPassData.setData(dataBuilder.toString());
			bulkPassData.setFromComponent("DataStore");
			bulkPassData.setToComponent("Client");

			List<Integer> processingResults = computingApi.processPassData(bulkPassData);
			System.out.println("Bulk processing results: " + processingResults);
		}

		return new ArrayList<>(storedNumbers);
	}


	public boolean validateNumber(int number) {
		// USE computingApi: Use computing API for validation
		boolean basicValidation = number >= 0;
		if (computingApi != null) {
			try {
				// Create PassData and process it to validate
				PassData validationPassData = computingApi.passData(number);
				List<Integer> validationResults = computingApi.processPassData(validationPassData);

				// If processing succeeds and returns results, consider it valid
				return basicValidation && validationResults != null && !validationResults.isEmpty();
			} catch (Exception e) {
				return false;
			}
		}
		return basicValidation;
	}


	public boolean processRequest() {
		System.out.println("Processing data storage request");

		// USE computingApi: Process all stored numbers
		if (computingApi != null && !storedNumbers.isEmpty()) {
			System.out.println("Processing " + storedNumbers.size() + " numbers with ComputingApi");

			// Process each number individually
			for (Integer number : storedNumbers) {
				PassData passData = computingApi.passData(number);
				List<Integer> results = computingApi.processPassData(passData);
				System.out.println("Number " + number + " processed with results: " + results);
			}

			// Also process as a batch
			PassData batchPassData = new PassData();
			batchPassData.setData("Batch processing of " + storedNumbers.size() + " numbers");
			batchPassData.setFromComponent("DataStore");
			batchPassData.setToComponent("BatchProcessor");

			List<Integer> batchResults = computingApi.processPassData(batchPassData);
			System.out.println("Batch processing completed with " + batchResults.size() + " results");
		}

		return !storedNumbers.isEmpty();
	}

	// Help method that uses computingApi
	public List<Integer> processAllNumbers() {
		if (computingApi != null && !storedNumbers.isEmpty()) {
			List<Integer> allResults = new ArrayList<>();
			for (Integer number : storedNumbers) {
				PassData passData = computingApi.passData(number);
				List<Integer> results = computingApi.processPassData(passData);
				allResults.addAll(results);
			}
			return allResults;
		}
		return new ArrayList<>();
	}

	// help method to get stored data count
	public int getStoredDataCount() {
		return storedNumbers.size();
	}

	// Help method to clear storage using computingApi notification
	public boolean clearStorage() {
		int previousSize = storedNumbers.size();
		if (previousSize > 0) {
			// USE computingApi: Notify about storage clearance
			if (computingApi != null) {
				PassData clearancePassData = new PassData();
				clearancePassData.setData("Clearing " + previousSize + " items from storage");
				clearancePassData.setFromComponent("DataStore");
				clearancePassData.setToComponent("Cleanup");

				List<Integer> clearanceResults = computingApi.processPassData(clearancePassData);
				System.out.println("Storage clearance processed with results: " + clearanceResults);
			}

			storedNumbers.clear();
		}

		return previousSize > 0;
	}
}