package numberlettercountfetching;

import java.util.ArrayList;
import java.util.List;
<<<<<<< HEAD
import java.util.logging.Logger;

=======
>>>>>>> main
import numberlettercountdatastoring.DataStoreApi;

public class FetchApiImpl implements FetchApi {
	private static final Logger logger = Logger.getLogger(FetchApiImpl.class.getName());
	private DataStoreApi dataStoreApi;
	private List<Integer> storedData = new ArrayList<>();

	public void setDataStoreApi(DataStoreApi dataStoreApi) {
		this.dataStoreApi = dataStoreApi;
	}

	public List<Integer> insertRequest(FetchRequest fetchRequest) {
<<<<<<< HEAD
		try {
			// Validate parameter
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

			// Validate each number
			for (Integer num : data) {
				if (num == null) {
					logger.warning("Skipping null number in request");
					continue;
				}
				if (num < 0) {
					logger.warning("Skipping negative number: " + num);
					continue;
				}
				storedData.add(num);
				logger.info("Stored number: " + num);
			}

			return new ArrayList<>(data); // Return copy

		} catch (Exception e) {
			logger.severe("Error in insertRequest: " + e.getMessage());
			return List.of(-1); // Error indicator
		}
	}

	public List<Integer> fetchAllData() {
		try {
			logger.info("Fetching " + storedData.size() + " items");
			return new ArrayList<>(storedData); // Return copy for safety

		} catch (Exception e) {
			logger.severe("Error in fetchAllData: " + e.getMessage());
			return new ArrayList<>(); // Return empty list on error
		}
	}

	public void processRequest() {
		try {
			logger.info("Processing fetch request with " + storedData.size() + " items");
			// Processing logic here

		} catch (Exception e) {
			logger.severe("Error in processRequest: " + e.getMessage());
			// Void method - just log the error
		}
	}

	public boolean validateNumber(int number) {
		try {
			// Simple validation - numbers must be non-negative
			boolean isValid = number >= 0;
			logger.info("Number " + number + " validation: " + isValid);
			return isValid;

		} catch (Exception e) {
			logger.severe("Error in validateNumber: " + e.getMessage());
			return false; // Default to invalid on error
		}
	}

	// Helper method to get stored data
=======
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

>>>>>>> main
	public List<Integer> getStoredData() {
		return new ArrayList<>(storedData);
	}

<<<<<<< HEAD
	// Helper method to clear stored data
	public void clearStoredData() {
		try {
			int count = storedData.size();
			storedData.clear();
			logger.info("Cleared " + count + " items from storage");
		} catch (Exception e) {
			logger.severe("Error clearing stored data: " + e.getMessage());
		}
=======
	public int getStoredDataCount() {
		return storedData.size();
>>>>>>> main
	}
}