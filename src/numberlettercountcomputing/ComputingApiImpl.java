package numberlettercountcomputing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import numberlettercountdatastoring.DataStoreApi;
import numberlettercountfetching.FetchApi;

public class ComputingApiImpl implements ComputingApi {
	private static final Logger logger = Logger.getLogger(ComputingApiImpl.class.getName());
	private FetchApi fetchApi;
	private DataStoreApi dataStoreApi;

	public ComputingApiImpl() {
		logger.info("ComputingApiImpl initialized without dependencies");
	}

	public ComputingApiImpl(FetchApi fetchApi, DataStoreApi dataStoreApi) {
		// Validation for constructor parameters
		if (fetchApi == null) {
			logger.warning("FetchApi dependency is null - some features may not work");
		}
		if (dataStoreApi == null) {
			logger.warning("DataStoreApi dependency is null - some features may not work");
		}

		this.fetchApi = fetchApi;
		this.dataStoreApi = dataStoreApi;
		logger.info("ComputingApiImpl initialized with dependencies");
	}

	public void setFetchApi(FetchApi fetchApi) {
		// No validation needed - null is acceptable (dependency might be optional)
		this.fetchApi = fetchApi;
		logger.info("FetchApi dependency set");
	}

	public void setDataStoreApi(DataStoreApi dataStoreApi) {
		// No validation needed - null is acceptable (dependency might be optional)
		this.dataStoreApi = dataStoreApi;
		logger.info("DataStoreApi dependency set");
	}

	public String initalize(List<Integer> inputData) {
<<<<<<< HEAD
		try {
			// Parameter validation
			if (inputData == null && fetchApi != null) {
				logger.info("Input data is null, attempting to fetch from FetchApi");
				inputData = fetchApi.fetchAllData();
=======
		//		if (inputData == null && fetchApi != null) {
		//			inputData = fetchApi.fetchAllData();
		//		}

		if (inputData == null || inputData.isEmpty()) {
			return "";
		}

		// Process data storage request
		//		if (dataStoreApi != null) {
		//			dataStoreApi.processRequest();
		//		}

		Extract extract = extractData();
		System.out.println("Extracting data: " + extract);

		ProcessData processData = processData();
		processData.setInputData(inputData.toString());

		String[] numberWords = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};

		StringBuilder result = new StringBuilder();
		for (int i = 0; i < inputData.size(); i++) {
			int num = inputData.get(i);
			if (num >= 0 && num <= 9) {
				result.append(numberWords[num]);
			} else {
				result.append(num);
>>>>>>> main
			}

			if (inputData == null || inputData.isEmpty()) {
				logger.warning("Input data is null or empty after fetch attempt");
				return "ERROR: No input data provided or available";
			}

			// Validate individual elements in the list
			for (Integer num : inputData) {
				if (num == null) {
					return "ERROR: Input list contains null elements";
				}
				if (num < 0) {
					return "ERROR: Negative numbers are not supported: " + num;
				}
			}

			// Process data storage request with error handling
			if (dataStoreApi != null) {
				try {
					boolean processed = dataStoreApi.processRequest();
					logger.info("Data storage request processed: " + processed);
				} catch (Exception e) {
					logger.warning("Data storage processing failed: " + e.getMessage());
				}
			}

			Extract extract = extractData();
			logger.info("Extracting data: " + extract);

			ProcessData processData = processData();
			processData.setInputData(inputData.toString());

			String[] numberWords = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};

			StringBuilder result = new StringBuilder();
			for (int i = 0; i < inputData.size(); i++) {
				int num = inputData.get(i);
				if (num >= 0 && num <= 9) {
					result.append(numberWords[num]);
				} else {
					result.append(num);
				}

				if (i < inputData.size() - 1) {
					result.append(",");
				}
			}

			processData.setOutputData(result.toString());
			logger.info("Processing data: " + processData);

			return result.toString();

		} catch (Exception e) {
			logger.severe("Unexpected error in initialize method: " + e.getMessage());
			return "ERROR: Initialization failed - " + e.getMessage();
		}
	}

	public List<Integer> compute() {
<<<<<<< HEAD
		try {
			List<Integer> dataToCompute = null;

			// Attempt to fetch data with error handling
			if (fetchApi != null) {
				try {
					dataToCompute = fetchApi.fetchAllData();
				} catch (Exception e) {
					logger.warning("Failed to fetch data from FetchApi: " + e.getMessage());
				}
			}

			if (dataToCompute == null && dataStoreApi != null) {
				try {
					dataToCompute = dataStoreApi.fetchAllData();
				} catch (Exception e) {
					logger.warning("Failed to fetch data from DataStoreApi: " + e.getMessage());
				}
			}

			// Fallback to default data with validation
			if (dataToCompute == null) {
				logger.info("No data available from dependencies, using default data");
				dataToCompute = Arrays.asList(1, 2, 3);
			}

			// Validate the computed data
			if (dataToCompute.isEmpty()) {
				logger.warning("Computed data is empty");
				return new ArrayList<>();
			}

			PassData passData = passData();
			passData.setData("Computing number to word conversion");
			logger.info("Passing data: " + passData);

			return new ArrayList<>(dataToCompute); // Return defensive copy

		} catch (Exception e) {
			logger.severe("Unexpected error in compute method: " + e.getMessage());
			return Arrays.asList(-1); // Sentinel value for error
		}
=======
		List<Integer> dataToCompute = null;
		//		if (fetchApi != null) {
		//			dataToCompute = fetchApi.fetchAllData();
		//		}

		//		if (dataToCompute == null && dataStoreApi != null) {
		//			dataToCompute = dataStoreApi.fetchAllData();
		//		}

		if (dataToCompute == null) {
			dataToCompute = Arrays.asList(1, 2, 3);
		}

		PassData passData = passData();
		passData.setData("Computing number to word conversion");
		System.out.println("Passing data: " + passData);

		return dataToCompute;
>>>>>>> main
	}

	public String writeResult(String result, String delimiters) {
		try {
			// Parameter validation
			if (result == null) {
				return "ERROR: Result cannot be null";
			}
			if (delimiters == null) {
				delimiters = ","; // Default delimiter
				logger.info("Delimiters parameter was null, using default");
			}

			if (result.trim().isEmpty()) {
				return "ERROR: Result cannot be empty";
			}

			SendInfo sendInfo = sendInfo();
			sendInfo.setData(result);
			sendInfo.setDestination("output");
			logger.info("Sending info: " + sendInfo);

			return "Processed: " + result + " with delimiters: " + delimiters;

		} catch (Exception e) {
			logger.severe("Unexpected error in writeResult method: " + e.getMessage());
			return "ERROR: Failed to write result";
		}
	}

	public void insertRequest() {
		try {
			logger.info("Insert request processed");

<<<<<<< HEAD
			// Process data storage request with error handling
			if (dataStoreApi != null) {
				try {
					boolean processed = dataStoreApi.processRequest();
					logger.info("Data storage request processed: " + processed);
				} catch (Exception e) {
					logger.warning("Data storage processing failed: " + e.getMessage());
				}
			}

			Extract extract = extractData();
			extract.setSource("user input");
			extract.setExtractedData("number data");
			logger.info("Extracted: " + extract);

			ProcessData processData = processData();
			processData.setProcessingType("number conversion");
			logger.info("Processing: " + processData);

			PassData passData = passData();
			passData.setFromComponent("compute");
			passData.setToComponent("storage");
			logger.info("Passing: " + passData);

			SendInfo sendInfo = sendInfo();
			sendInfo.setDestination("client");
			logger.info("Sending: " + sendInfo);

			RecieveInfo recieveInfo = recieveInfo();
			recieveInfo.setSource("external system");
			logger.info("Receiving: " + recieveInfo);

		} catch (Exception e) {
			logger.severe("Unexpected error in insertRequest method: " + e.getMessage());
			// Void method - exception is logged but not propagated
		}
=======
		//		// Process data storage request
		//		if (dataStoreApi != null) {
		//			boolean processed = dataStoreApi.processRequest();
		//			System.out.println("Data storage request processed: " + processed);
		//		}

		Extract extract = extractData();
		extract.setSource("user input");
		extract.setExtractedData("number data");
		System.out.println("Extracted: " + extract);

		ProcessData processData = processData();
		processData.setProcessingType("number conversion");
		System.out.println("Processing: " + processData);

		PassData passData = passData();
		passData.setFromComponent("compute");
		passData.setToComponent("storage");
		System.out.println("Passing: " + passData);

		SendInfo sendInfo = sendInfo();
		sendInfo.setDestination("client");
		System.out.println("Sending: " + sendInfo);

		RecieveInfo recieveInfo = recieveInfo();
		recieveInfo.setSource("external system");
		System.out.println("Receiving: " + recieveInfo);
>>>>>>> main
	}

	public Extract extractData() {
		// No parameters to validate
		Extract extract = new Extract();
		extract.setExtractionMethod("number parsing");
		return extract;
	}

	public SendInfo sendInfo() {
		// No parameters to validate
		SendInfo sendInfo = new SendInfo();
		sendInfo.setDestination("default destination");
		return sendInfo;
	}

	public RecieveInfo recieveInfo() {
		// No parameters to validate
		RecieveInfo recieveInfo = new RecieveInfo();
		recieveInfo.setSource("default source");
		return recieveInfo;
	}

	public ProcessData processData() {
		// No parameters to validate
		ProcessData processData = new ProcessData();
		processData.setProcessingType("number_to_words");
		return processData;
	}

	public PassData passData() {
		// No parameters to validate
		PassData passData = new PassData();
		passData.setFromComponent("computing module");
		passData.setToComponent("storage module");
		return passData;
	}

	public List<Integer> processPassData(PassData passData) {
		try {
			// Parameter validation
			if (passData == null) {
				logger.warning("PassData parameter is null");
				return Arrays.asList(-1); // Sentinel value for error
			}

			logger.info("Processing pass data: " + passData);
			List<Integer> resultList = new ArrayList<>();

			String data = passData.getData();
			if (data != null && !data.isEmpty()) {
				int letterCount = data.replaceAll("[^a-zA-Z]", "").length();
				logger.info("Letter count in pass data: " + letterCount);
				resultList.add(letterCount);
			}

			if (passData.getFromComponent() != null) {
				resultList.add(passData.getFromComponent().length());
			}
			if (passData.getToComponent() != null) {
				resultList.add(passData.getToComponent().length());
			}

			return resultList;

		} catch (Exception e) {
			logger.severe("Unexpected error in processPassData method: " + e.getMessage());
			return Arrays.asList(-1); // Sentinel value for error
		}
	}

	public PassData passData(int number) {
		try {
			// Parameter validation
			if (number < 0) {
				logger.warning("Negative number provided: " + number);
				// Continue processing but mark as invalid
			}

			PassData passData = new PassData();

			String[] numberWords = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
			String word = "";

			if (number >= 0 && number <= 9) {
				word = numberWords[number];
			} else {
				word = String.valueOf(number);
			}

			passData.setData(word);
			passData.setFromComponent("number_converter");
			passData.setToComponent("output_processor");

			logger.info("Created PassData for number " + number + ": " + passData);

			// Validate with dependencies if available
			if (fetchApi != null) {
				try {
					boolean isValid = fetchApi.validateNumber(number);
					if (!isValid) {
						passData.setData("INVALID: " + word);
						logger.warning("Number validation failed by FetchApi: " + number);
					}
				} catch (Exception e) {
					logger.warning("FetchApi validation failed: " + e.getMessage());
				}
			}

			if (dataStoreApi != null) {
				try {
					boolean isValid = dataStoreApi.validateNumber(number);
					if (!isValid) {
						passData.setData("INVALID_DATASTORE: " + word);
						logger.warning("Number validation failed by DataStoreApi: " + number);
					}
				} catch (Exception e) {
					logger.warning("DataStoreApi validation failed: " + e.getMessage());
				}
			}

			return passData;

		} catch (Exception e) {
			logger.severe("Unexpected error in passData method: " + e.getMessage());
			// Return a default PassData object indicating error
			PassData errorPassData = new PassData();
			errorPassData.setData("ERROR: Failed to process number");
			errorPassData.setFromComponent("error_handler");
			errorPassData.setToComponent("recovery");
			return errorPassData;
		}
	}
}