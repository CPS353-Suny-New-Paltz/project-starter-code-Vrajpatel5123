package numberlettercountcomputing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import numberlettercountdatastoring.DataStoreApi;
import numberlettercountfetching.FetchApi;

public class ComputingApiImpl implements ComputingApi {
	private FetchApi fetchApi;
	private DataStoreApi dataStoreApi;

	public ComputingApiImpl() {}

	public ComputingApiImpl(FetchApi fetchApi, DataStoreApi dataStoreApi) {
		this.fetchApi = fetchApi;
		this.dataStoreApi = dataStoreApi;
	}

	public void setFetchApi(FetchApi fetchApi) {
		this.fetchApi = fetchApi;
	}

	public void setDataStoreApi(DataStoreApi dataStoreApi) {
		this.dataStoreApi = dataStoreApi;
	}

	public String initalize(List<Integer> inputData) {
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
			}

			if (i < inputData.size() - 1) {
				result.append(",");
			}
		}

		processData.setOutputData(result.toString());
		System.out.println("Processing data: " + processData);

		return result.toString();
	}

	public List<Integer> compute() {
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
	}

	public String writeResult(String result, String delimiters) {
		SendInfo sendInfo = sendInfo();
		sendInfo.setData(result);
		sendInfo.setDestination("output");
		System.out.println("Sending info: " + sendInfo);

		return "Processed: " + result + " with delimiters: " + delimiters;
	}

	public void insertRequest() {
		System.out.println("Insert request processed");

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
	}

	public Extract extractData() {
		Extract extract = new Extract();
		extract.setExtractionMethod("number parsing");
		return extract;
	}

	public SendInfo sendInfo() {
		SendInfo sendInfo = new SendInfo();
		sendInfo.setDestination("default destination");
		return sendInfo;
	}

	public RecieveInfo recieveInfo() {
		RecieveInfo recieveInfo = new RecieveInfo();
		recieveInfo.setSource("default source");
		return recieveInfo;
	}

	public ProcessData processData() {
		ProcessData processData = new ProcessData();
		processData.setProcessingType("number_to_words");
		return processData;
	}

	public PassData passData() {
		PassData passData = new PassData();
		passData.setFromComponent("computing module");
		passData.setToComponent("storage module");
		return passData;
	}

	public List<Integer> processPassData(PassData passData) {
		System.out.println("Processing pass data: " + passData);
		List<Integer> resultList = new ArrayList<>();

		if (passData != null) {
			String data = passData.getData();
			if (data != null && !data.isEmpty()) {
				int letterCount = data.replaceAll("[^a-zA-Z]", "").length();
				System.out.println("Letter count in pass data: " + letterCount);
				resultList.add(letterCount);
			}

			if (passData.getFromComponent() != null) {
				resultList.add(passData.getFromComponent().length());
			}
			if (passData.getToComponent() != null) {
				resultList.add(passData.getToComponent().length());
			}
		}

		return resultList;
	}

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

		if (fetchApi != null) {
			boolean isValid = fetchApi.validateNumber(number);
			if (!isValid) {
				passData.setData("INVALID: " + word);
			}
		}

		if (dataStoreApi != null) {
			boolean isValid = dataStoreApi.validateNumber(number);
			if (!isValid) {
				passData.setData("INVALID_DATASTORE: " + word);
			}
		}

		return passData;
	}
}