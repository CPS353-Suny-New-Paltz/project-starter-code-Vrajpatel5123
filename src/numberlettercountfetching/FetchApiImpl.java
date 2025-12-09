package numberlettercountfetching;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import numberlettercountdatastoring.DataStoreApi;
import numberlettercountdatastoring.DataRequest; 
import numberlettercountcomputing.ComputingApi;
import numberlettercountcomputing.PassData; 
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FetchApiImpl implements FetchApi {
	private static final Logger logger = Logger.getLogger(FetchApiImpl.class.getName());
	private DataStoreApi dataStoreApi;
	private ComputingApi computingApi;
	private List<BigInteger> storedData = new ArrayList<>();
	private List<BigInteger> lastResults = new ArrayList<>();

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

	// Process file method - returns letter counts as BigInteger
	public boolean processFile(String inputFile, String outputFile) {
		try {
			Path inputPath = Paths.get(inputFile);
			List<BigInteger> numbers = new ArrayList<>();

			if (!Files.exists(inputPath)) {
				logger.severe("Input file not found: " + inputFile);
				return false;
			}

			for (String line : Files.readAllLines(inputPath)) {
				try {
					// Parse as BigInteger to handle arbitrarily large numbers
					numbers.add(new BigInteger(line.trim()));
				} catch (NumberFormatException e) {
					logger.warning("Skipping invalid line: " + line);
				}
			}

			if (numbers.isEmpty()) {
				logger.warning("No valid numbers in input file");
				return false;
			}

			logger.info("Processing " + numbers.size() + " numbers from file: " + inputFile);

			// Process each number
			List<BigInteger> letterCounts = new ArrayList<>();
			if (computingApi == null) {
				logger.severe("ComputingApi not available");
				return false;
			}

			for (BigInteger number : numbers) {
				// Check if number fits in int for ComputingApi
				BigInteger letterCount = BigInteger.valueOf(-1);
				if (number.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) <= 0 && 
						number.compareTo(BigInteger.valueOf(0)) >= 0) {

					// Number fits in int, use ComputingApi
					int intNumber = number.intValue();
					PassData passData = computingApi.passData(intNumber);
					List<Integer> results = computingApi.processPassData(passData);

					if (!results.isEmpty()) {
						letterCount = BigInteger.valueOf(results.get(0));
						logger.info("Processed number " + number + " -> result: " + letterCount);
					}
				} else {
					// Large number - use custom conversion
					letterCount = convertLargeNumberToLetterCount(number);
					logger.info("Processed large number " + number + " -> result: " + letterCount);
				}

				letterCounts.add(letterCount);
			}

			this.lastResults = letterCounts;

			// Store the numbers via insertRequest
			FetchRequest fetchRequest = new FetchRequest(numbers);
			List<BigInteger> insertResult = this.insertRequest(fetchRequest);

			// Write results to output file
			StringBuilder output = new StringBuilder();
			for (int i = 0; i < letterCounts.size(); i++) {
				output.append(letterCounts.get(i));
				if (i < letterCounts.size() - 1) {
					output.append(",");
				}
			}

			Path outputPath = Paths.get(outputFile);
			Files.write(outputPath, output.toString().getBytes(),
					StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

			logger.info("Successfully wrote " + letterCounts.size() + " results to: " + outputFile);
			return true;

		} catch (IOException e) {
			logger.severe("Error processing file: " + e.getMessage());
			return false;
		} catch (Exception e) {
			logger.severe("Unexpected error in processFile: " + e.getMessage());
			return false;
		}
	}

	// Helper method to convert large numbers to letter counts
	private BigInteger convertLargeNumberToLetterCount(BigInteger number) {
		try {
			// This is a simplified conversion for large numbers
			// In a real implementation, you'd need proper number-to-words conversion
			if (number.equals(BigInteger.ZERO)) {
				return BigInteger.valueOf(4); // "zero" = 4 letters
			}

			// Placeholder: estimate based on digit count
			int digitCount = number.toString().length();
			// Rough estimate: each digit contributes about 3-5 letters on average
			return BigInteger.valueOf(digitCount * 4);
		} catch (Exception e) {
			logger.warning("Error converting large number: " + e.getMessage());
			return BigInteger.valueOf(-1);
		}
	}

	public List<BigInteger> getLastResults() {
		return new ArrayList<>(lastResults);
	}

	// FetchApi interface implementation
	@Override
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

			List<BigInteger> letterCounts = new ArrayList<>();

			for (BigInteger number : data) {
				if (number == null) {
					logger.warning("Skipping null number in request");
					letterCounts.add(BigInteger.valueOf(-1));
					continue;
				}

				if (!validateNumber(number)) {
					logger.warning("Skipping invalid number: " + number);
					letterCounts.add(BigInteger.valueOf(-1));
					continue;
				}

				storedData.add(number);
				logger.info("Stored number: " + number);

				BigInteger letterCount = BigInteger.valueOf(-1);
				if (computingApi != null) {
					try {
						if (number.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) <= 0 && 
								number.compareTo(BigInteger.valueOf(0)) >= 0) {

							int intNumber = number.intValue();
							PassData passData = computingApi.passData(intNumber);
							List<Integer> results = computingApi.processPassData(passData);

							if (!results.isEmpty()) {
								letterCount = BigInteger.valueOf(results.get(0));
								logger.info("Letter count for " + number + " = " + letterCount);
							}
						} else {
							letterCount = convertLargeNumberToLetterCount(number);
							logger.info("Large number letter count for " + number + " = " + letterCount);
						}
					} catch (Exception e) {
						logger.warning("ComputingApi failed for " + number + ": " + e.getMessage());
					}
				}

				letterCounts.add(letterCount);
			}

			boolean allFailed = true;
			for (BigInteger count : letterCounts) {
				if (count != null && !count.equals(BigInteger.valueOf(-1))) {
					allFailed = false;
					break;
				}
			}

			if (allFailed) {
				logger.warning("All letter counts are -1");
				return List.of(BigInteger.valueOf(-1));
			}

			logger.info("Successfully processed " + letterCounts.size() + " numbers");
			return letterCounts;

		} catch (Exception e) {
			logger.severe("Error in insertRequest: " + e.getMessage());
			return List.of(BigInteger.valueOf(-1));
		}
	}

	@Override
	public boolean validateNumber(BigInteger number) {
		try {
			if (number == null) {
				logger.warning("Number is null");
				return false;
			}

			boolean basicValidation = number.compareTo(BigInteger.ZERO) >= 0;
			logger.info("Basic validation for number " + number + ": " + basicValidation);

			if (!basicValidation) {
				return false;
			}

			if (dataStoreApi != null) {
				try {
					// Check if number fits in int for DataStoreApi validation
					boolean dataStoreValidation = false;
					if (number.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) <= 0 && 
							number.compareTo(BigInteger.valueOf(Integer.MIN_VALUE)) >= 0) {
						dataStoreValidation = dataStoreApi.validateNumber(number.intValue());
					} else {
						// Large numbers - use basic validation
						dataStoreValidation = basicValidation;
					}

					logger.info("DataStoreApi validation for number " + number + ": " + dataStoreValidation);
					return dataStoreValidation;
				} catch (Exception e) {
					logger.warning("DataStoreApi validation failed: " + e.getMessage());
				}
			}

			return basicValidation;

		} catch (Exception e) {
			logger.severe("Error in validateNumber: " + e.getMessage());
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

	public void clearStoredData() {
		try {
			int count = storedData.size();
			storedData.clear();
			logger.info("Cleared " + count + " items from storage");
		} catch (Exception e) {
			logger.severe("Error clearing stored data: " + e.getMessage());
		}
	}

	// Helper method for computing all stored data
	public List<BigInteger> computeAllStoredData() {
		List<BigInteger> allComputedResults = new ArrayList<>();

		if (computingApi == null) {
			logger.warning("ComputingApi not available for computation");
			return allComputedResults;
		}

		try {
			for (BigInteger number : storedData) {
				if (number != null) {
					if (number.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) <= 0 && 
							number.compareTo(BigInteger.valueOf(0)) >= 0) {

						PassData passData = computingApi.passData(number.intValue());
						List<Integer> computedResults = computingApi.processPassData(passData);

						if (computedResults != null) {
							for (Integer result : computedResults) {
								allComputedResults.add(BigInteger.valueOf(result));
							}
						}
					} else {
						BigInteger letterCount = convertLargeNumberToLetterCount(number);
						allComputedResults.add(letterCount);
					}
				}
			}
		} catch (Exception e) {
			logger.severe("Error in computeAllStoredData: " + e.getMessage());
		}

		return allComputedResults;
	}

	public boolean storeComputedResults(List<BigInteger> computedResults) {
		if (dataStoreApi == null || computedResults == null || computedResults.isEmpty()) {
			return false;
		}

		int storedCount = 0;
		try {
			for (BigInteger result : computedResults) {
				if (result != null) {
					// Create DataRequest - use result as request ID if it fits in int
					DataRequest dataRequest;
					if (result.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) <= 0 && 
							result.compareTo(BigInteger.valueOf(Integer.MIN_VALUE)) >= 0) {
						dataRequest = new DataRequest(result.intValue());
					} else {
						dataRequest = new DataRequest(0); // Use default ID for large results
					}

					int insertResult = dataStoreApi.insertRequest(dataRequest);
					if (insertResult >= 0) {
						storedCount++;
					}
				}
			}
			return storedCount > 0;
		} catch (Exception e) {
			logger.severe("Error storing computed results: " + e.getMessage());
			return false;
		}
	}
}