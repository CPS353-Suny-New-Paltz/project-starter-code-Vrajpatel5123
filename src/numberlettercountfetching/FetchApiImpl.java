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
	private List<Integer> lastResults = new ArrayList<>();

	// This constructor matches the CoordinatorApiImpl constructor
	public FetchApiImpl() {
		// These will be set later via setter methods
		logger.info("FetchApiImpl created - dependencies need to be set");
	}

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

			List<Integer> letterCounts = new ArrayList<>(); // Store letter counts

			// Process each number
			for (Integer number : data) {
				if (number == null) {
					logger.warning("Skipping null number in request");
					letterCounts.add(-1); // Error indicator
					continue;
				}

				// Use internal validation
				if (!validateNumber(number)) {
					logger.warning("Skipping invalid number: " + number);
					letterCounts.add(-1); // Error indicator
					continue;
				}

				// Store the valid number
				storedData.add(number);
				logger.info("Stored number: " + number);

				// Get letter count from ComputingApi
				int letterCount = -1;
				if (computingApi != null) {
					try {
						// Create PassData from the number
						PassData passData = computingApi.passData(number);

						// Process the PassData to get results
						List<Integer> computedResults = computingApi.processPassData(passData);

						if (computedResults != null && !computedResults.isEmpty()) {
							// First result is the letter count
							letterCount = computedResults.get(0);
							logger.info("Letter count for number " + number + ": " + letterCount);
						}

						// If dataStoreApi is available, store the computed results
						if (dataStoreApi != null && computedResults != null && !computedResults.isEmpty()) {
							// Convert results to comma-separated string
							StringBuilder dataContent = new StringBuilder();
							for (int i = 0; i < computedResults.size(); i++) {
								if (i > 0) {
									dataContent.append(",");
								}
								dataContent.append(computedResults.get(i));
							}

							DataRequest dataRequest = new DataRequest(number, "FetchApi", dataContent.toString());
							int insertResult = dataStoreApi.insertRequest(dataRequest);
							logger.info("DataStoreApi insert result: " + insertResult);
						}
					} catch (Exception e) {
						logger.warning("ComputingApi processing failed for number " + number + ": " + e.getMessage());
					}
				} else {
					logger.warning("ComputingApi not available for number: " + number);
				}

				letterCounts.add(letterCount);
			}

			// Check if all results are errors
			boolean allErrors = true;
			for (Integer count : letterCounts) {
				if (count != -1) {
					allErrors = false;
					break;
				}
			}

			if (allErrors) {
				logger.warning("No valid letter counts generated");
				return List.of(-1);
			}

			logger.info("Successfully processed " + letterCounts.size() + " numbers");
			logger.info("Letter counts: " + letterCounts);
			return letterCounts; // Return letter counts, not original numbers!

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
					DataRequest dataRequest = new DataRequest(result, "computed_result", result.toString());
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