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
		logger.info("DataStoreApiImpl initialized without ComputingApi dependency");
	}

	public DataStoreApiImpl(ComputingApi computingApi) {
		// Validation for constructor parameter
		if (computingApi == null) {
			logger.warning("ComputingApi dependency is null - some features may not work");
		}
		this.computingApi = computingApi;
		logger.info("DataStoreApiImpl initialized with ComputingApi dependency");
	}

	public void setComputingApi(ComputingApi computingApi) {
		// No validation needed - null is acceptable (dependency might be optional)
		this.computingApi = computingApi;
		logger.info("ComputingApi dependency set");
	}

	public int insertRequest(DataRequest dataRequest) {
		try {
			// Parameter validation
			if (dataRequest == null) {
				logger.warning("DataRequest parameter is null");
				return -1; // failure code
			}

			if (dataRequest.getRequestId() < 0) {
				logger.warning("Invalid request ID: " + dataRequest.getRequestId());
				return -1;
			}

			logger.info("Processing data request: " + dataRequest);

			// Extract numbers from data request and store them
			try {
				String dataContent = dataRequest.getDataContent();
				if (dataContent == null || dataContent.trim().isEmpty()) {
					logger.warning("Data content is null or empty");
					return -1;
				}

				String[] numberStrings = dataContent.split(",");
				for (String numStr : numberStrings) {
					try {
						int number = Integer.parseInt(numStr.trim());

						// Validate number before storing
						if (number < 0) {
							logger.warning("Skipping negative number: " + number);
							continue;
						}

						storedNumbers.add(number);
						logger.fine("Stored number: " + number);

						// USE computingApi: Process the number using available methods
						if (computingApi != null) {
							try {
								PassData passData = computingApi.passData(number);
								logger.info("Created PassData for number " + number + ": " + passData);

								List<Integer> processedResults = computingApi.processPassData(passData);
								logger.info("Processed results: " + processedResults);
							} catch (Exception e) {
								logger.warning("ComputingApi processing failed for number " + number + ": " + e.getMessage());
							}
						}
					} catch (NumberFormatException e) {
						logger.warning("Invalid number format in data content: '" + numStr + "'");
						// Continue processing other numbers
					}
				}
				return 0; // success
			} catch (Exception e) {
				logger.severe("Error processing data request content: " + e.getMessage());
				return -1;
			}
		} catch (Exception e) {
			logger.severe("Unexpected error in insertRequest method: " + e.getMessage());
			return -1;
		}
	}

	public List<Integer> fetchAllData() {
		try {
			logger.info("Fetching all stored data, count: " + storedNumbers.size());

			// USE computingApi: Process the stored numbers before returning
			if (computingApi != null && !storedNumbers.isEmpty()) {
				logger.info("Processing stored data with ComputingApi");

				try {
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
					logger.info("Bulk processing results: " + processingResults);
				} catch (Exception e) {
					logger.warning("Bulk processing with ComputingApi failed: " + e.getMessage());
				}
			}

			// Return defensive copy
			return new ArrayList<>(storedNumbers);

		} catch (Exception e) {
			logger.severe("Unexpected error in fetchAllData method: " + e.getMessage());
			return new ArrayList<>(); // Return empty list on error
		}
	}

	public boolean validateNumber(int number) {
		try {
			// Basic validation
			boolean basicValidation = number >= 0;
			if (!basicValidation) {
				logger.warning("Number validation failed - negative number: " + number);
				return false;
			}

			// USE computingApi: Use computing API for additional validation
			if (computingApi != null) {
				try {
					// Create PassData and process it to validate
					PassData validationPassData = computingApi.passData(number);
					List<Integer> validationResults = computingApi.processPassData(validationPassData);

					// If processing succeeds and returns results, consider it valid
					boolean computingValidation = validationResults != null && !validationResults.isEmpty();
					logger.info("ComputingApi validation result for " + number + ": " + computingValidation);

					return computingValidation;
				} catch (Exception e) {
					logger.warning("ComputingApi validation failed for number " + number + ": " + e.getMessage());
					// Fall back to basic validation
					return basicValidation;
				}
			}

			return basicValidation;

		} catch (Exception e) {
			logger.severe("Unexpected error in validateNumber method: " + e.getMessage());
			return false; // Default to invalid on error
		}
	}

	public boolean processRequest() {
		try {
			logger.info("Processing data storage request, stored items: " + storedNumbers.size());

			// USE computingApi: Process all stored numbers
			if (computingApi != null && !storedNumbers.isEmpty()) {
				logger.info("Processing " + storedNumbers.size() + " numbers with ComputingApi");

				// Process each number individually
				for (Integer number : storedNumbers) {
					try {
						PassData passData = computingApi.passData(number);
						List<Integer> results = computingApi.processPassData(passData);
						logger.info("Number " + number + " processed with results: " + results);
					} catch (Exception e) {
						logger.warning("Failed to process number " + number + ": " + e.getMessage());
					}
				}

				// Also process as a batch
				try {
					PassData batchPassData = new PassData();
					batchPassData.setData("Batch processing of " + storedNumbers.size() + " numbers");
					batchPassData.setFromComponent("DataStore");
					batchPassData.setToComponent("BatchProcessor");

					List<Integer> batchResults = computingApi.processPassData(batchPassData);
					logger.info("Batch processing completed with " + batchResults.size() + " results");
				} catch (Exception e) {
					logger.warning("Batch processing failed: " + e.getMessage());
				}
			}

			boolean success = !storedNumbers.isEmpty();
			logger.info("Process request completed: " + success);
			return success;

		} catch (Exception e) {
			logger.severe("Unexpected error in processRequest method: " + e.getMessage());
			return false;
		}
	}

	// Helper method that uses computingApi
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
						logger.warning("Failed to process number " + number + " in processAllNumbers: " + e.getMessage());
					}
				}
				return allResults;
			}
			return new ArrayList<>();
		} catch (Exception e) {
			logger.severe("Unexpected error in processAllNumbers method: " + e.getMessage());
			return new ArrayList<>();
		}
	}

	// Helper method to get stored data count
	public int getStoredDataCount() {
		return storedNumbers.size();
	}

	// Helper method to clear storage using computingApi notification
	public boolean clearStorage() {
		try {
			int previousSize = storedNumbers.size();
			if (previousSize > 0) {
				// USE computingApi: Notify about storage clearance
				if (computingApi != null) {
					try {
						PassData clearancePassData = new PassData();
						clearancePassData.setData("Clearing " + previousSize + " items from storage");
						clearancePassData.setFromComponent("DataStore");
						clearancePassData.setToComponent("Cleanup");

						List<Integer> clearanceResults = computingApi.processPassData(clearancePassData);
						logger.info("Storage clearance processed with results: " + clearanceResults);
					} catch (Exception e) {
						logger.warning("Clearance notification failed: " + e.getMessage());
					}
				}

				storedNumbers.clear();
				logger.info("Storage cleared, removed " + previousSize + " items");
				return true;
			}

			logger.info("Storage was already empty");
			return false;

		} catch (Exception e) {
			logger.severe("Unexpected error in clearStorage method: " + e.getMessage());
			return false;
		}
	}
}