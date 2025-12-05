package numberlettercountcomputing;

import java.util.ArrayList;
import java.util.List;
<<<<<<< HEAD

public class ComputingApiImpl implements ComputingApi {

	public ComputingApiImpl() {}

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
=======
import java.util.logging.Logger;

public class ComputingApiImpl implements ComputingApi {
	private static final Logger logger = Logger.getLogger(ComputingApiImpl.class.getName());

	public ComputingApiImpl() {
		logger.info("ComputingApiImpl created");
	}

	public PassData passData(int number) {
		try {
			// Parameter validation
			if (number < 0) {
				logger.warning("Negative number provided: " + number);
				// Continue processing but note the validation failure
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
			return passData;

		} catch (Exception e) {
			logger.severe("Error in passData: " + e.getMessage());
			// Return default PassData indicating error
			PassData errorData = new PassData();
			errorData.setData("ERROR");
			errorData.setFromComponent("error_handler");
			errorData.setToComponent("recovery");
			return errorData;
		}
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
			} else {
				logger.info("PassData has no data content");
			}

			String fromComponent = passData.getFromComponent();
			if (fromComponent != null) {
				resultList.add(fromComponent.length());
			} else {
				logger.info("FromComponent is null");
			}

			String toComponent = passData.getToComponent();
			if (toComponent != null) {
				resultList.add(toComponent.length());
			} else {
				logger.info("ToComponent is null");
			}

			// If result list is empty, add a marker
			if (resultList.isEmpty()) {
				logger.warning("ProcessPassData produced empty results");
				resultList.add(0); // Return 0 as a valid but empty result
			}

			return resultList;

		} catch (Exception e) {
			logger.severe("Error in processPassData: " + e.getMessage());
			return Arrays.asList(-1); // Sentinel value for error
		}
>>>>>>> main
	}
}