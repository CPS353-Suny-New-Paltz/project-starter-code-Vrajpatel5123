package numberlettercountcomputing;
import java.util.ArrayList;
import java.util.List;

import numberlettercountdatastoring.DataStoreApi;
import numberlettercountfetching.FetchApi;

public class ComputingApiImpl implements ComputingApi {
	private FetchApi fetchApi;
	private DataStoreApi dataStoreApi;


	// Add setters for dependency injection
	public void setFetchApi(FetchApi fetchApi) {
		this.fetchApi = fetchApi;
	}

	public void setDataStoreApi(DataStoreApi dataStoreApi) {
		this.dataStoreApi = dataStoreApi;
	}

	@Override
	public String initalize(List<Integer> inputData) {
		if (inputData == null || inputData.isEmpty()) {
			return "";
		}

		// Use extractData to process the input
		Extract extract = extractData();
		System.out.println("Extracting data: " + extract);

		// Use processData during computation
		ProcessData processData = processData();
		processData.setInputData(inputData.toString());

		//        // Simple number to word mapping for demonstration
		String[] numberWords = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
		//        
		//        StringBuilder result = new StringBuilder();
		//        for (int i = 0; i < inputData.size(); i++) {
		//            int num = inputData.get(i);
		//            if (num >= 0 && num <= 9) {
		//                result.append(numberWords[num]);
		//            } else {
		//                result.append(num); // Fallback to number if out of range
		//            }
		//            
		//            if (i < inputData.size() - 1) {
		//                result.append(",");
		//            }
		//        }
		// Convert List<Integer> to a comma-separated string
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < inputData.size(); i++) {
			int num = inputData.get(i);
			if (num >= 0 && num <= 9) {
				result.append(numberWords[num]);
			} else {
				result.append(num); // Fallback to number if out of range
			}

			if (i < inputData.size() - 1) {
				result.append(",");
			}
		}

		// Update processData with result
		processData.setOutputData(result.toString());
		System.out.println("Processing data: " + processData);

		return result.toString(); // Returns "one,two,three" for input [1,2,3]
	}
	@Override
	public List<Integer> compute() {
		// Implementation for compute logic
		// Use passData during computation
		PassData passData = passData();
		passData.setData("Computing number to word conversion");
		System.out.println("Passing data: " + passData);

		return List.of(1, 2, 3);
	}

	@Override
	public String writeResult(String result, String delimiters) {
		// Use sendInfo to send the result
		SendInfo sendInfo = sendInfo();
		sendInfo.setData(result);
		sendInfo.setDestination("output");
		System.out.println("Sending info: " + sendInfo);

		return "Processed: " + result + " with delimiters: " + delimiters;
	}

	@Override
	public void insertRequest() {
		System.out.println("Insert request processed");

		// Use all objects in the workflow
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

	@Override
	public Extract extractData() {
		Extract extract = new Extract();
		extract.setExtractionMethod("number parsing");
		return extract;
	}

	@Override
	public SendInfo sendInfo() {
		SendInfo sendInfo = new SendInfo();
		sendInfo.setDestination("default destination");
		return sendInfo;
	}

	@Override
	public RecieveInfo recieveInfo() {
		RecieveInfo recieveInfo = new RecieveInfo();
		recieveInfo.setSource("default source");
		return recieveInfo;
	}

	@Override
	public ProcessData processData() {
		ProcessData processData = new ProcessData();
		processData.setProcessingType("number_to_words");
		return processData;
	}

	@Override
	public PassData passData() {
		PassData passData = new PassData();
		passData.setFromComponent("computing module");
		passData.setToComponent("storage module");
		return passData;
	}
}