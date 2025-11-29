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
		if (dataRequest == null) {
			return -1;
		}

			if (dataRequest.getRequestId() < 0) {
				logger.warning("Invalid request ID: " + dataRequest.getRequestId());
				return -1;
			}

		try {
			String[] numberStrings = dataRequest.getDataContent().split(",");
			for (String numStr : numberStrings) {
				int number = Integer.parseInt(numStr.trim());
				storedNumbers.add(number);

				if (computingApi != null) {
					PassData passData = computingApi.passData(number);
					System.out.println("Created PassData for number " + number + ": " + passData);

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

	public List<Integer> fetchAllData() {
		return new ArrayList<>(storedNumbers);
	}

	public int getStoredDataCount() {
		return storedNumbers.size();
	}
}