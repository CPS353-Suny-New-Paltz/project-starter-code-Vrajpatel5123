package numberlettercountfetching;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import numberlettercountdatastoring.DataStoreApi;

public class FetchApiImpl implements FetchApi {
	private static final Logger logger = Logger.getLogger(FetchApiImpl.class.getName());
	private DataStoreApi dataStoreApi;
	private List<Integer> storedData = new ArrayList<>();

	public void setDataStoreApi(DataStoreApi dataStoreApi) {
		this.dataStoreApi = dataStoreApi;
		logger.info("DataStoreApi dependency set");
	}

	public List<Integer> insertRequest(FetchRequest fetchRequest) {
		try {
			// Parameter validation
			if (fetchRequest == null) {
				logger.warning("FetchRequest is null");
				return List.of(-1); // Error indicator
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

				if (number < 0) {
					logger.warning("Skipping negative number: " + number);
					continue;
				}

				storedData.add(number);
				validCount++;
				logger.info("Stored number: " + number);

				// Validate with DataStoreApi if available
				if (dataStoreApi != null) {
					try {
						boolean isValid = dataStoreApi.validateNumber(number);
						logger.info("Number " + number + " validation: " + isValid);
					} catch (Exception e) {
						logger.warning("DataStoreApi validation failed for number " + number + ": " + e.getMessage());
					}
				}
			}

			if (validCount == 0) {
				logger.warning("No valid numbers found in request");
				return List.of(-1);
			}

			logger.info("Successfully stored " + validCount + " numbers");
			return new ArrayList<>(data); // Return defensive copy

		} catch (Exception e) {
			logger.severe("Error in insertRequest: " + e.getMessage());
			return List.of(-1); // Error indicator
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
			return false; // Default to invalid on error
		}
	}

	public List<Integer> getStoredData() {
		try {
			logger.info("Returning " + storedData.size() + " stored items");
			return new ArrayList<>(storedData); // Return defensive copy
		} catch (Exception e) {
			logger.severe("Error in getStoredData: " + e.getMessage());
			return new ArrayList<>(); // Return empty list on error
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
}