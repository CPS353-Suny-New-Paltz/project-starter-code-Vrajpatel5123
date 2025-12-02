package numberlettercountcomputing;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ComputingApiImpl implements ComputingApi {
<<<<<<< HEAD
    private static final Logger logger = Logger.getLogger(ComputingApiImpl.class.getName());
    private FetchApi fetchApi;
    private DataStoreApi dataStoreApi;
=======
>>>>>>> main

    public ComputingApiImpl() {
        logger.info("ComputingApiImpl created");
    }

<<<<<<< HEAD
    public ComputingApiImpl(FetchApi fetchApi, DataStoreApi dataStoreApi) {
        this.fetchApi = fetchApi;
        this.dataStoreApi = dataStoreApi;
        logger.info("ComputingApiImpl created with dependencies");
    }

    public void setFetchApi(FetchApi fetchApi) {
        this.fetchApi = fetchApi;
    }

    public void setDataStoreApi(DataStoreApi dataStoreApi) {
        this.dataStoreApi = dataStoreApi;
    }

    public String initalize(List<Integer> inputData) {
        try {
            // Validate input parameter
            if (inputData == null) {
                logger.warning("Input data is null");
                return "ERROR: Input data cannot be null";
            }
            
            if (inputData.isEmpty()) {
                logger.warning("Input data is empty");
                return "ERROR: Input data cannot be empty";
            }

            // Check for null elements in list
            for (Integer num : inputData) {
                if (num == null) {
                    return "ERROR: Input list contains null values";
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
            logger.severe("Error in initialize: " + e.getMessage());
            return "ERROR: Failed to initialize - " + e.getMessage();
        }
    }

    public List<Integer> compute() {
        try {
            List<Integer> dataToCompute = null;

            // Use default data if nothing else works
            if (dataToCompute == null) {
                logger.info("Using default data");
                dataToCompute = Arrays.asList(1, 2, 3);
            }

            PassData passData = passData();
            passData.setData("Computing number to word conversion");
            logger.info("Passing data: " + passData);

            return new ArrayList<>(dataToCompute); // Return copy for safety

        } catch (Exception e) {
            logger.severe("Error in compute: " + e.getMessage());
            return Arrays.asList(-1); // Error indicator
        }
    }

    public String writeResult(String result, String delimiters) {
        try {
            // Validate parameters
            if (result == null) {
                return "ERROR: Result cannot be null";
            }
            
            if (result.trim().isEmpty()) {
                return "ERROR: Result cannot be empty";
            }
            
            if (delimiters == null) {
                delimiters = ","; // Default value
                logger.info("Using default delimiter");
            }

            SendInfo sendInfo = sendInfo();
            sendInfo.setData(result);
            sendInfo.setDestination("output");
            logger.info("Sending info: " + sendInfo);

            return "Processed: " + result + " with delimiters: " + delimiters;

        } catch (Exception e) {
            logger.severe("Error in writeResult: " + e.getMessage());
            return "ERROR: Failed to write result";
        }
    }

    public void insertRequest() {
        try {
            logger.info("Insert request processed");

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
            logger.severe("Error in insertRequest: " + e.getMessage());
            // Void method - just log the error
        }
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
=======
	public PassData passData(int number) {
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

		System.out.println("Created PassData for number " + number + ": " + passData);
		return passData;
	}
>>>>>>> main

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
            // Validate parameter
            if (passData == null) {
                logger.warning("PassData is null");
                return Arrays.asList(-1); // Error indicator
            }

<<<<<<< HEAD
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
            logger.severe("Error in processPassData: " + e.getMessage());
            return Arrays.asList(-1); // Error indicator
        }
    }

    public PassData passData(int number) {
        try {
            // Validate parameter
            if (number < 0) {
                logger.warning("Negative number provided: " + number);
                // Continue processing but note it's invalid
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
                        logger.warning("FetchApi marked number as invalid: " + number);
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
                        logger.warning("DataStoreApi marked number as invalid: " + number);
                    }
                } catch (Exception e) {
                    logger.warning("DataStoreApi validation failed: " + e.getMessage());
                }
            }

            return passData;

        } catch (Exception e) {
            logger.severe("Error in passData: " + e.getMessage());
            // Return basic PassData indicating error
            PassData errorData = new PassData();
            errorData.setData("ERROR");
            errorData.setFromComponent("error");
            errorData.setToComponent("recovery");
            return errorData;
        }
    }
=======
		return resultList;
	}
>>>>>>> main
}