package numberlettercountcomputing;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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
	}
}