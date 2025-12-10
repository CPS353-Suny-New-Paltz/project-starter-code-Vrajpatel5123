package numberlettercountfetching;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import numberlettercountdatastoring.DataStoreApi;
import numberlettercountdatastoring.DataStoreApiImpl;
import numberlettercountcomputing.ComputingApi;
import numberlettercountcomputing.PassData;

public class FetchApiImpl implements FetchApi {
	private static final Logger logger = Logger.getLogger(FetchApiImpl.class.getName());
	private DataStoreApi dataStoreApi;
	private ComputingApi computingApi;

	public FetchApiImpl() {
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

	public List<BigInteger> insertRequest(FetchRequest fetchRequest) {
		try {
			if (fetchRequest == null) {
				logger.warning("FetchRequest is null");
				return List.of(BigInteger.valueOf(-1));
			}

			List<BigInteger> data = fetchRequest.getData();
			if (data == null || data.isEmpty()) {
				logger.warning("FetchRequest data is null or empty");
				return List.of(BigInteger.valueOf(-1));
			}

			List<BigInteger> results = new ArrayList<>();
			List<String> resultStrings = new ArrayList<>(); // For file writing

			for (BigInteger number : data) {
				if (number == null) {
					logger.warning("Skipping null number in request");
					results.add(BigInteger.valueOf(-1));
					resultStrings.add("-1");
					continue;
				}

				// Validate the number
				if (!validateNumber(number)) {
					logger.warning("Skipping invalid number: " + number);
					results.add(BigInteger.valueOf(-1));
					resultStrings.add("-1");
					continue;
				}

				// Compute letter count via ComputingApi
				BigInteger letterCount = BigInteger.valueOf(-1);
				if (computingApi != null) {
					try {
						if (number.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) <= 0 && 
								number.compareTo(BigInteger.valueOf(0)) >= 0) {

							int intNumber = number.intValue();

							// Pass data to ComputingApi
							PassData passData = computingApi.passData(intNumber);

							// Get results from ComputingApi
							List<Integer> computeResults = computingApi.processPassData(passData);

							if (computeResults != null && !computeResults.isEmpty()) {
								letterCount = BigInteger.valueOf(computeResults.get(0));
								logger.info("Computed letter count for " + number + " = " + letterCount);
							}
						} else {
							logger.warning("Number " + number + " is too large for ComputingApi");
							letterCount = BigInteger.valueOf(-2);
						}
					} catch (Exception e) {
						logger.warning("ComputingApi failed for " + number + ": " + e.getMessage());
					}
				}

				results.add(letterCount);
				resultStrings.add(letterCount.toString()); // Store as string for file writing
			}

			// Write results to file via DataStoreApi
			if (dataStoreApi != null && dataStoreApi instanceof DataStoreApiImpl) {
				DataStoreApiImpl dataStoreImpl = (DataStoreApiImpl) dataStoreApi;
				// FIX: Write just the numbers, not formatted strings
				dataStoreImpl.writeResultsToFile("manualTestOutput.txt", resultStrings);
			}

			return results;

		} catch (Exception e) {
			logger.severe("Error in insertRequest: " + e.getMessage());
			return List.of(BigInteger.valueOf(-1));
		}
	}

	public boolean validateNumber(BigInteger number) {
		try {
			if (number == null) {
				logger.warning("Number is null");
				return false;
			}

			// Delegate validation to DataStoreApi if available
			if (dataStoreApi != null) {
				try {
					if (number.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) <= 0 && 
							number.compareTo(BigInteger.valueOf(Integer.MIN_VALUE)) >= 0) {
						return dataStoreApi.validateNumber(number.intValue());
					}
				} catch (Exception e) {
					logger.warning("DataStoreApi validation failed: " + e.getMessage());
				}
			}

			// Basic validation - non-negative
			boolean isValid = number.compareTo(BigInteger.ZERO) >= 0;
			logger.info("Validation for number " + number + ": " + isValid);

			return isValid;

		} catch (Exception e) {
			logger.severe("Error in validateNumber: " + e.getMessage());
			return false;
		}
	}
}