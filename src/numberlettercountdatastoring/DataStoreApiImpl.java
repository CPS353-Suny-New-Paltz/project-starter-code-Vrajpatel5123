package numberlettercountdatastoring;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import numberlettercountcomputing.ComputingApi;
import numberlettercountcomputing.PassData;

public class DataStoreApiImpl implements DataStoreApi {
	private static final Logger logger = Logger.getLogger(DataStoreApiImpl.class.getName());
	private ComputingApi computingApi;
	private List<Integer> storedNumbers = new ArrayList<>();

	public DataStoreApiImpl() {
		logger.info("DataStoreApiImpl created");
	}

	public DataStoreApiImpl(ComputingApi computingApi) {
		this.computingApi = computingApi;
		logger.info("DataStoreApiImpl created with ComputingApi");
	}

	public void setComputingApi(ComputingApi computingApi) {
		this.computingApi = computingApi;
	}

	public int insertRequest(DataRequest dataRequest) {
		try {
			// Validate parameter
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
				logger.warning("Data content is empty");
				return -1;
			}

			logger.info("Processing data request: " + dataRequest);

			// Process the data content
			String[] numberStrings = dataContent.split(",");
			int storedCount = 0;

			for (String numStr : numberStrings) {
				try {
					int number = Integer.parseInt(numStr.trim());

					// Validate number
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
							List<Integer> processedResults = computingApi.processPassData(passData);
							logger.info("Processed number " + number + " with results: " + processedResults);
						} catch (Exception e) {
							logger.warning("ComputingApi processing failed for number " + number + ": " + e.getMessage());
						}
					}

				} catch (NumberFormatException e) {
					logger.warning("Invalid number format: '" + numStr + "'");
					// Continue with next number
				}
			}

			if (storedCount == 0) {
				logger.warning("No valid numbers stored from request");
				return -1;
			}

			logger.info("Successfully stored " + storedCount + " numbers");
			return 0; // Success code

		} catch (Exception e) {
			logger.severe("Error in insertRequest: " + e.getMessage());
			return -1; // Error code
		}
	}

	public List<Integer> fetchAllData() {
		try {
			logger.info("Fetching " + storedNumbers.size() + " stored numbers");

			// Process with ComputingApi if available
			if (computingApi != null && !storedNumbers.isEmpty()) {
				try {
					// Create batch data for processing
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
					logger.info("Bulk processing completed with " + processingResults.size() + " results");
				} catch (Exception e) {
					logger.warning("Bulk processing failed: " + e.getMessage());
				}
			}

			return new ArrayList<>(storedNumbers); // Return copy

		} catch (Exception e) {
			logger.severe("Error in fetchAllData: " + e.getMessage());
			return new ArrayList<>(); // Return empty list on error
		}
	}

	public boolean validateNumber(int number) {
		try {
			// Basic validation
			if (number < 0) {
				logger.warning("Negative number invalid: " + number);
				return false;
			}

			// Additional validation with ComputingApi if available
			if (computingApi != null) {
				try {
					PassData validationPassData = computingApi.passData(number);
					List<Integer> validationResults = computingApi.processPassData(validationPassData);

					boolean isValid = validationResults != null && !validationResults.isEmpty();
					logger.info("ComputingApi validation for " + number + ": " + isValid);
					return isValid;

				} catch (Exception e) {
					logger.warning("ComputingApi validation failed: " + e.getMessage());
					// Fall back to basic validation
				}
			}

			logger.info("Basic validation for " + number + ": true");
			return true; // Basic validation passed

		} catch (Exception e) {
			logger.severe("Error in validateNumber: " + e.getMessage());
			return false; // Default to invalid on error
		}
	}

	public boolean processRequest() {
		try {
			logger.info("Processing data storage request with " + storedNumbers.size() + " items");

			if (storedNumbers.isEmpty()) {
				logger.warning("No data to process");
				return false;
			}

			// Process with ComputingApi if available
			if (computingApi != null) {
				// Process each number individually
				for (Integer number : storedNumbers) {
					try {
						PassData passData = computingApi.passData(number);
						List<Integer> results = computingApi.processPassData(passData);
						logger.info("Processed number " + number + " with " + results.size() + " results");
					} catch (Exception e) {
						logger.warning("Failed to process number " + number + ": " + e.getMessage());
					}
				}

				// Process as batch
				try {
					PassData batchPassData = new PassData();
					batchPassData.setData("Batch of " + storedNumbers.size() + " numbers");
					batchPassData.setFromComponent("DataStore");
					batchPassData.setToComponent("BatchProcessor");

					List<Integer> batchResults = computingApi.processPassData(batchPassData);
					logger.info("Batch processing completed with " + batchResults.size() + " results");
				} catch (Exception e) {
					logger.warning("Batch processing failed: " + e.getMessage());
				}
			}

			logger.info("Process request completed successfully");
			return true;

		} catch (Exception e) {
			logger.severe("Error in processRequest: " + e.getMessage());
			return false;
		}
	}

	// Helper methods with error handling
	public List<Integer> processAllNumbers() {
		try {
			if (computingApi != null && !storedNumbers.isEmpty()) {
				List<Integer> allResults = new ArrayList<>();
				for (Integer number : storedNumbers) {
					try {
						PassData passData = computingApi.passData(number);
						List<Integer> results = computingApi.processPassData(passData);
						allResults.addAll(results);
					} catch (Exception e) {
						logger.warning("Failed to process number " + number);
					}
				}
				return allResults;
			}
			return new ArrayList<>();
		} catch (Exception e) {
			logger.severe("Error in processAllNumbers: " + e.getMessage());
			return new ArrayList<>();
		}
	}

	public int getStoredDataCount() {
		return storedNumbers.size();
	}

	public boolean clearStorage() {
		try {
			int previousSize = storedNumbers.size();
			if (previousSize > 0) {
				// Notify ComputingApi if available
				if (computingApi != null) {
					try {
						PassData clearancePassData = new PassData();
						clearancePassData.setData("Clearing " + previousSize + " items");
						clearancePassData.setFromComponent("DataStore");
						clearancePassData.setToComponent("Cleanup");

						computingApi.processPassData(clearancePassData);
						logger.info("Notified ComputingApi about clearance");
					} catch (Exception e) {
						logger.warning("Clearance notification failed: " + e.getMessage());
					}
				}

				storedNumbers.clear();
				logger.info("Cleared " + previousSize + " items from storage");
				return true;
			}

			logger.info("Storage was already empty");
			return false;

		} catch (Exception e) {
			logger.severe("Error in clearStorage: " + e.getMessage());
			return false;
		}
	}

	// Helper to check if storage is empty
	public boolean isEmpty() {
		return storedNumbers.isEmpty();
	}

	// Helper to get storage info
	public String getStorageInfo() {
		return "DataStore contains " + storedNumbers.size() + " numbers";
	}
}