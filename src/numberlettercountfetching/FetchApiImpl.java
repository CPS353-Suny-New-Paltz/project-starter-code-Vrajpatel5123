package numberlettercountfetching;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import numberlettercountdatastoring.DataStoreApi;
import numberlettercountdatastoring.DataRequest; 
import numberlettercountcomputing.ComputingApi;
import numberlettercountcomputing.PassData; 

public class FetchApiImpl implements FetchApi {
	private static final Logger logger = Logger.getLogger(FetchApiImpl.class.getName());
	private DataStoreApi dataStoreApi;
	private ComputingApi computingApi;
	private List<Integer> storedData = new ArrayList<>();

	public void setDataStoreApi(DataStoreApi dataStoreApi) {
		this.dataStoreApi = dataStoreApi;
		logger.info("DataStoreApi dependency set");
	}

	public void setComputingApi(ComputingApi computingApi) {
		this.computingApi = computingApi;
		logger.info("ComputingApi dependency set");
	}

	public List<Integer> insertRequest(FetchRequest fetchRequest) {
		try {
			// Parameter validation
			if (fetchRequest == null) {
				logger.warning("FetchRequest is null");
				return List.of(-1);
			}

			List<Integer> data = fetchRequest.getData();
			if (data == null) {
				logger.warning("FetchRequest data is null");
				return List.of(-1);
			}

			if (data.isEmpty()) {
				logger.warning("FetchRequest data is empty");
				return List.of(-1);
			}

			// Validate and store each number
			int validCount = 0;
			for (Integer number : data) {
				if (number == null) {
					logger.warning("Skipping null number in request");
					continue;
				}

				// Use internal validation
				if (!validateNumber(number)) {
					logger.warning("Skipping invalid number: " + number);
					continue;
				}

				// Store the valid number
				storedData.add(number);
				validCount++;
				logger.info("Stored number: " + number);

				// If computingApi is available, process the number
				if (computingApi != null) {
					try {
						// Create PassData from the number
						PassData passData = computingApi.passData(number);

						// Process the PassData to get results
						List<Integer> computedResults = computingApi.processPassData(passData);

						logger.info("Computed results for number " + number + ": " + computedResults);

						// If dataStoreApi is available, store the computed results
						if (dataStoreApi != null && computedResults != null && !computedResults.isEmpty()) {
							for (Integer result : computedResults) {
								if (result != null) {
									// Create DataRequest for each result
									DataRequest dataRequest = new DataRequest(result);
									int insertResult = dataStoreApi.insertRequest(dataRequest);
									logger.info("DataStoreApi insert result for " + result + ": " + insertResult);
								}
							}
						}
					} catch (Exception e) {
						logger.warning("ComputingApi processing failed for number " + number + ": " + e.getMessage());
					}
				}
			}

			if (validCount == 0) {
				logger.warning("No valid numbers found in request");
				return List.of(-1);
			}

			logger.info("Successfully processed " + validCount + " numbers");
			return new ArrayList<>(data); // Return defensive copy

		} catch (Exception e) {
			logger.severe("Error in insertRequest: " + e.getMessage());
			return List.of(-1);
		}
	}

	public boolean validateNumber(int number) {
		try {
			// Basic validation
			boolean basicValidation = number >= 0;
			logger.info("Basic validation for number " + number + ": " + basicValidation);

			if (!basicValidation) {
				return false;
			}

			// Additional validation with DataStoreApi if available
			if (dataStoreApi != null) {
				try {
					boolean dataStoreValidation = dataStoreApi.validateNumber(number);
					logger.info("DataStoreApi validation for number " + number + ": " + dataStoreValidation);
					return dataStoreValidation;
				} catch (Exception e) {
					logger.warning("DataStoreApi validation failed: " + e.getMessage());
					// Fall back to basic validation
				}
			}

			return basicValidation;

		} catch (Exception e) {
			logger.severe("Error in validateNumber: " + e.getMessage());
			return false;
		}
	}

	public List<Integer> getStoredData() {
		try {
			logger.info("Returning " + storedData.size() + " stored items");
			return new ArrayList<>(storedData);
		} catch (Exception e) {
			logger.severe("Error in getStoredData: " + e.getMessage());
			return new ArrayList<>();
		}
	}

	public int getStoredDataCount() {
		return storedData.size();
	}

	// Helper method with error handling
	public void clearStoredData() {
		try {
			int count = storedData.size();
			storedData.clear();
			logger.info("Cleared " + count + " items from storage");
		} catch (Exception e) {
			logger.severe("Error clearing stored data: " + e.getMessage());
		}
	}

	// New method to coordinate computation for all stored data
	public List<Integer> computeAllStoredData() {
		List<Integer> allComputedResults = new ArrayList<>();

		if (computingApi == null) {
			logger.warning("ComputingApi not available for computation");
			return allComputedResults;
		}

		try {
			for (Integer number : storedData) {
				if (number != null) {
					// Create PassData for each number
					PassData passData = computingApi.passData(number);

					// Process PassData to get results
					List<Integer> computedResults = computingApi.processPassData(passData);

					if (computedResults != null) {
						allComputedResults.addAll(computedResults);
						logger.info("Computed results for " + number + ": " + computedResults);
					}
				}
			}
		} catch (Exception e) {
			logger.severe("Error in computeAllStoredData: " + e.getMessage());
		}

		return allComputedResults;
	}

	// New method to store computed results via DataStoreApi
	public boolean storeComputedResults(List<Integer> computedResults) {
		if (dataStoreApi == null) {
			logger.warning("DataStoreApi not available for storing computed results");
			return false;
		}

		if (computedResults == null || computedResults.isEmpty()) {
			logger.warning("No computed results to store");
			return false;
		}

		int storedCount = 0;
		try {
			for (Integer result : computedResults) {
				if (result != null) {
					// Create DataRequest for each result
					DataRequest dataRequest = new DataRequest(result);
					int insertResult = dataStoreApi.insertRequest(dataRequest);

					if (insertResult >= 0) { // Assuming non-negative return means success
						storedCount++;
						logger.info("Successfully stored computed result: " + result);
					} else {
						logger.warning("Failed to store computed result: " + result);
					}
				}
			}
			logger.info("Stored " + storedCount + " computed results via DataStoreApi");
			return storedCount > 0;
		} catch (Exception e) {
			logger.severe("Error storing computed results: " + e.getMessage());
			return false;
		}
	}
}