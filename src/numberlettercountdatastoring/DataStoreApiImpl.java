package numberlettercountdatastoring;

import java.util.ArrayList;
import java.util.List;
import numberlettercountcomputing.ComputingApi;
import numberlettercountcomputing.PassData;

public class DataStoreApiImpl implements DataStoreApi {
	private ComputingApi computingApi;
	private List<Integer> storedNumbers = new ArrayList<>();

<<<<<<< HEAD
	public DataStoreApiImpl() {}
=======
	public DataStoreApiImpl() {
		logger.info("DataStoreApiImpl created without ComputingApi");
	}
>>>>>>> main

	public DataStoreApiImpl(ComputingApi computingApi) {
		this.computingApi = computingApi;
	}

	public void setComputingApi(ComputingApi computingApi) {
		this.computingApi = computingApi;
		logger.info("ComputingApi dependency set");
	}

	public int insertRequest(DataRequest dataRequest) {
<<<<<<< HEAD
		if (dataRequest == null) {
			return -1;
=======
		try {
			// Parameter validation
			if (dataRequest == null) {
				logger.warning("DataRequest is null");
				return -1; // Error code
			}

			if (dataRequest.getRequestId() < 0) {
				logger.warning("Invalid request ID: " + dataRequest.getRequestId());
				return -1;
			}

			String dataContent = dataRequest.getDataContent();
			if (dataContent == null || dataContent.trim().isEmpty()) {
				logger.warning("Data content is empty for request ID: " + dataRequest.getRequestId());
				return -1;
			}

			logger.info("Processing data request: " + dataRequest);

			// Process the data content
			String[] numberStrings = dataContent.split(",");
			int storedCount = 0;

			for (String numStr : numberStrings) {
				try {
					int number = Integer.parseInt(numStr.trim());

					// Validate number before storing
					if (number < 0) {
						logger.warning("Skipping negative number: " + number);
						continue;
					}

					storedNumbers.add(number);
					storedCount++;
					logger.info("Stored number: " + number);

					// Process with ComputingApi if available
					if (computingApi != null) {
						try {
							PassData passData = computingApi.passData(number);
							logger.info("Created PassData for number " + number + ": " + passData);

							List<Integer> processedResults = computingApi.processPassData(passData);
							logger.info("Processed results for number " + number + ": " + processedResults);
						} catch (Exception e) {
							logger.warning("ComputingApi processing failed for number " + number + ": " + e.getMessage());
						}
					}

				} catch (NumberFormatException e) {
					logger.warning("Invalid number format: '" + numStr + "' in request ID: " + dataRequest.getRequestId());
					// Continue with next number
				}
			}

			if (storedCount == 0) {
				logger.warning("No valid numbers stored from request ID: " + dataRequest.getRequestId());
				return -1;
			}

			logger.info("Successfully stored " + storedCount + " numbers from request ID: " + dataRequest.getRequestId());
			return 0; // Success code

		} catch (Exception e) {
			logger.severe("Error in insertRequest: " + e.getMessage());
			return -1; // Error code
>>>>>>> main
		}

<<<<<<< HEAD
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

=======
>>>>>>> main
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
<<<<<<< HEAD
=======

			logger.info("Basic validation passed for number: " + number);

			// Additional validation with ComputingApi if available
			if (computingApi != null) {
				try {
					PassData validationPassData = computingApi.passData(number);
					List<Integer> validationResults = computingApi.processPassData(validationPassData);

					boolean isValid = validationResults != null && !validationResults.isEmpty();
					logger.info("ComputingApi validation for " + number + ": " + isValid);
					return isValid;

				} catch (Exception e) {
					logger.warning("ComputingApi validation failed for number " + number + ": " + e.getMessage());
					// Fall back to basic validation
				}
			}

			// If we get here, basic validation passed and either ComputingApi is null or failed
			return true;

		} catch (Exception e) {
			logger.severe("Error in validateNumber: " + e.getMessage());
			return false; // Default to invalid on error
>>>>>>> main
		}
		return basicValidation;
	}

	public List<Integer> fetchAllData() {
<<<<<<< HEAD
		return new ArrayList<>(storedNumbers);
=======
		try {
			logger.info("Fetching " + storedNumbers.size() + " stored numbers");

			// Return defensive copy
			return new ArrayList<>(storedNumbers);

		} catch (Exception e) {
			logger.severe("Error in fetchAllData: " + e.getMessage());
			return new ArrayList<>(); // Return empty list on error
		}
	}

	public boolean processRequest() {
		try {
			logger.info("Processing data storage request with " + storedNumbers.size() + " items");

			if (storedNumbers.isEmpty()) {
				logger.warning("No data to process");
				return false;
			}

			logger.info("Process request completed successfully for " + storedNumbers.size() + " items");
			return true;

		} catch (Exception e) {
			logger.severe("Error in processRequest: " + e.getMessage());
			return false;
		}
>>>>>>> main
	}

	public int getStoredDataCount() {
		return storedNumbers.size();
	}
<<<<<<< HEAD
=======

	// Helper method to clear storage
	public void clearStorage() {
		try {
			int previousSize = storedNumbers.size();
			storedNumbers.clear();
			logger.info("Cleared " + previousSize + " items from storage");
		} catch (Exception e) {
			logger.severe("Error in clearStorage: " + e.getMessage());
		}
	}

	// Helper method to check if storage is empty
	public boolean isEmpty() {
		return storedNumbers.isEmpty();
	}
>>>>>>> main
}