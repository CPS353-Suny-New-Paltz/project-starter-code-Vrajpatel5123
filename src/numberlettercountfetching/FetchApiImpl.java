package numberlettercountfetching;

import java.math.BigInteger;
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
<<<<<<< Updated upstream
	private ComputingApi computingApi;
=======
>>>>>>> Stashed changes
	private List<BigInteger> storedData = new ArrayList<>();

	public void setDataStoreApi(DataStoreApi dataStoreApi) {
		this.dataStoreApi = dataStoreApi;
		logger.info("DataStoreApi dependency set");
	}

<<<<<<< Updated upstream
	public void setComputingApi(ComputingApi computingApi) {
		this.computingApi = computingApi;
		logger.info("ComputingApi dependency set");
	}

=======
>>>>>>> Stashed changes
	public List<BigInteger> insertRequest(FetchRequest fetchRequest) {
		try {
			// Parameter validation
			if (fetchRequest == null) {
				logger.warning("FetchRequest is null");
				return List.of(BigInteger.valueOf(-1)); // Error indicator
			}

			List<BigInteger> data = fetchRequest.getData();
			if (data == null) {
				logger.warning("FetchRequest data is null");
				return List.of(BigInteger.valueOf(-1));
			}

			if (data.isEmpty()) {
				logger.warning("FetchRequest data is empty");
				return List.of(BigInteger.valueOf(-1));
			}

<<<<<<< Updated upstream
			List<BigInteger> letterCounts = new ArrayList<>(); // Store letter counts as BigInteger

			// Process each number
=======
			// Validate and store each number
			int validCount = 0;
>>>>>>> Stashed changes
			for (BigInteger number : data) {
				if (number == null) {
					logger.warning("Skipping null number in request");
					letterCounts.add(BigInteger.valueOf(-1)); // Error indicator
					continue;
				}

<<<<<<< Updated upstream
				// Validate the number
				if (!validateNumber(number)) {
					logger.warning("Skipping invalid number: " + number);
					letterCounts.add(BigInteger.valueOf(-1)); // Error indicator
					continue;
				}

				// Store the valid number
=======
				if (number.compareTo(BigInteger.ZERO) < 0) {
					logger.warning("Skipping negative number: " + number);
					continue;
				}

				// Check for memory constraints (optional, for very large lists)
				if (storedData.size() > 1000000) { // Arbitrary limit
					logger.warning("Storage capacity warning: " + storedData.size() + " items");
				}

>>>>>>> Stashed changes
				storedData.add(number);
				logger.info("Stored number: " + number);

				// Get letter count from ComputingApi
				BigInteger letterCount = BigInteger.valueOf(-1);
				if (computingApi != null) {
					try {
<<<<<<< Updated upstream
						// Check if number fits in int for ComputingApi
						if (number.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) <= 0 && 
								number.compareTo(BigInteger.valueOf(0)) >= 0) {

							int intNumber = number.intValue();

							// Create PassData from the number
							PassData passData = computingApi.passData(intNumber);

							// Process the PassData to get results
							List<Integer> computedResults = computingApi.processPassData(passData);

							if (computedResults != null && !computedResults.isEmpty()) {
								// First result is the letter count
								letterCount = BigInteger.valueOf(computedResults.get(0));
								logger.info("Letter count for number " + number + ": " + letterCount);
							}
						} else {
							// For numbers beyond int range, use alternative method
							letterCount = calculateLetterCountForLargeNumber(number);
							logger.info("Calculated letter count for large number " + number + ": " + letterCount);
						}

						// Store via DataStoreApi if possible and number fits in int
						if (dataStoreApi != null && 
								number.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) <= 0 && 
								number.compareTo(BigInteger.valueOf(0)) >= 0) {

							// Create a DataRequest with the number and its letter count
							String dataContent = number + "," + letterCount;
							DataRequest dataRequest = new DataRequest(number.intValue(), "FetchApi", dataContent);
							int insertResult = dataStoreApi.insertRequest(dataRequest);
							logger.info("DataStoreApi insert result: " + insertResult);
						}
=======
						// Convert BigInteger to int if needed, but check bounds
						boolean isValid = false;
						if (number.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) <= 0 && 
								number.compareTo(BigInteger.valueOf(Integer.MIN_VALUE)) >= 0) {
							isValid = dataStoreApi.validateNumber(number.intValue());
						} else {
							// For numbers beyond int range, use alternative validation
							isValid = validateLargeNumber(number);
						}
						logger.info("Number " + number + " validation: " + isValid);
>>>>>>> Stashed changes
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
			for (BigInteger count : letterCounts) {
				if (count != null && !count.equals(BigInteger.valueOf(-1))) {
					allErrors = false;
					break;
				}
			}

<<<<<<< Updated upstream
			if (allErrors) {
				logger.warning("No valid letter counts generated");
=======
			if (validCount == 0) {
				logger.warning("No valid numbers found in request");
>>>>>>> Stashed changes
				return List.of(BigInteger.valueOf(-1));
			}

			logger.info("Successfully processed " + letterCounts.size() + " numbers");
			return letterCounts; // Return letter counts, not original numbers!

		} catch (Exception e) {
			logger.severe("Error in insertRequest: " + e.getMessage());
<<<<<<< Updated upstream
			return List.of(BigInteger.valueOf(-1));
		}
	}

	// Helper method to calculate letter count for large numbers
	private BigInteger calculateLetterCountForLargeNumber(BigInteger number) {
		try {
			// Convert number to string for processing
			String numStr = number.toString();

			// For very large numbers, we'll use a simpler approach
			// This is a placeholder - in a real system, you'd need proper
			// number-to-words conversion for arbitrarily large numbers
			if (number.equals(BigInteger.ZERO)) {
				return BigInteger.valueOf(4); // "zero" has 4 letters
			}

			// Basic estimation or placeholder logic
			// In a full implementation, this would convert the large number to words
			// using algorithms for large number conversion
			return BigInteger.valueOf(numStr.length() * 3); // Rough estimation
		} catch (Exception e) {
			logger.warning("Error calculating letter count for large number: " + e.getMessage());
			return BigInteger.valueOf(-1);
		}
	}

	public boolean validateNumber(BigInteger number) {
		try {
=======
			return List.of(BigInteger.valueOf(-1)); // Error indicator
		}
	}

	public boolean validateNumber(BigInteger number) {
		try {
>>>>>>> Stashed changes
			if (number == null) {
				logger.warning("Number is null");
				return false;
			}

<<<<<<< Updated upstream
			// Basic validation - non-negative
=======
			// Basic validation
>>>>>>> Stashed changes
			boolean basicValidation = number.compareTo(BigInteger.ZERO) >= 0;
			logger.info("Basic validation for number " + number + ": " + basicValidation);

			if (!basicValidation) {
				return false;
			}

			// Check if number is within int range for DataStoreApi compatibility
			if (dataStoreApi != null) {
				try {
					boolean dataStoreValidation = false;

					if (number.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) <= 0 && 
							number.compareTo(BigInteger.valueOf(Integer.MIN_VALUE)) >= 0) {
						// Within int range
						dataStoreValidation = dataStoreApi.validateNumber(number.intValue());
					} else {
						// Beyond int range - use custom validation
						dataStoreValidation = validateLargeNumber(number);
					}

					logger.info("DataStoreApi validation for number " + number + ": " + dataStoreValidation);
					return dataStoreValidation;
				} catch (Exception e) {
					logger.warning("DataStoreApi validation failed: " + e.getMessage());
					// Fall back to basic validation
				}
			}

			// Custom validation for large numbers
			return validateLargeNumber(number);

		} catch (Exception e) {
			logger.severe("Error in validateNumber: " + e.getMessage());
			return false;
		}
	}

	// Helper method for validating large numbers
	private boolean validateLargeNumber(BigInteger number) {
		try {
			// Additional validation logic for large numbers
			// 1. Check if number is too large (optional limit)
			BigInteger maxAllowed = new BigInteger("10").pow(1000); // 10^1000
			if (number.compareTo(maxAllowed) > 0) {
				logger.warning("Number exceeds maximum allowed size: " + number);
				return false;
			}

			// 2. Check for common issues (like all zeros)
			if (number.equals(BigInteger.ZERO)) {
				return true; // Zero is valid
			}

			// 3. Check digit count (optional)
			int digitCount = number.toString().length();
			if (digitCount > 10000) {
				logger.warning("Number has excessive digits: " + digitCount);
				return false;
			}

			return true;
		} catch (Exception e) {
			logger.warning("Error in validateLargeNumber: " + e.getMessage());
			return false;
		}
	}

	public List<BigInteger> getStoredData() {
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

	public BigInteger getTotalSum() {
		try {
			BigInteger sum = BigInteger.ZERO;
			for (BigInteger num : storedData) {
				sum = sum.add(num);
			}
			return sum;
		} catch (Exception e) {
			logger.severe("Error calculating sum: " + e.getMessage());
			return BigInteger.ZERO;
		}
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

	// Method to handle very large batches
	public void insertBatch(List<BigInteger> batch) {
		try {
			if (batch == null || batch.isEmpty()) {
				logger.warning("Batch is empty");
				return;
			}

			logger.info("Processing batch of " + batch.size() + " numbers");

			// Process in chunks to avoid memory issues
			int chunkSize = 1000;
			for (int i = 0; i < batch.size(); i += chunkSize) {
				int end = Math.min(batch.size(), i + chunkSize);
				List<BigInteger> chunk = batch.subList(i, end);

				for (BigInteger number : chunk) {
					if (validateNumber(number)) {
						storedData.add(number);
					}
				}

				logger.info("Processed chunk " + (i/chunkSize + 1) + 
						", total stored: " + storedData.size());
			}

		} catch (Exception e) {
			logger.severe("Error in insertBatch: " + e.getMessage());
		}
	}
}