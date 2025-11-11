package numberlettercountfetching;

import java.util.Arrays;
import java.util.List;

import project.annotations.NetworkAPIPrototype;

public class FetchApiPrototype {

	@NetworkAPIPrototype
	public void prototype(FetchApi fetchapi) {
		System.out.println("=== Fetch API Prototype Demo ===");

		fetchapi.insertRequest(new FetchRequest(Arrays.asList(123)));    
		fetchapi.insertRequest(new IntFetchRequest(123));
		fetchapi.insertRequest(new StringFetchRequest("hello"));
		fetchapi.insertRequest(new ListFetchRequest(Arrays.asList(1, 2, 3)));

		// Uses the actual methods
		List<Integer> allData = fetchapi.fetchAllData();
		System.out.println("Fetched data: " + allData);

		boolean isValid = fetchapi.validateNumber(5);
		System.out.println("Number validation: " + isValid);

		System.out.println("=== Fetch API Prototype Demo Complete ===");
	}

	//	String inputSource;
	//
	//	String outputResult;
	//
	//
	//
	//	final String defaultDelimiters = ".;|";
	//
	//	private String delimiters;
	//
	//	@NetworkAPIPrototype
	//	public void prototype(FetchApi fetchapi)   {
	//
	//		fetchapi.insertRequest(new FetchRequest(Arrays.asList(123)));	
	//		fetchapi.insertRequest(new IntFetchRequest(123));
	//
	//		// For string
	//		fetchapi.insertRequest(new StringFetchRequest("hello"));
	//
	//		// For list
	//		fetchapi.insertRequest(new ListFetchRequest(Arrays.asList(1, 2, 3)));
	//		//User specify input location, output(file or database) and delimiter
	//		//Get back success or failed.
	//	}

	//	public  String getInputSource(String inputSource) {
	//		return inputSource;
	//	}
	//	
	//	public String getOutputResult(String outputResult) {
	//		return outputResult;
	//	}
	//	
	//	public String setDelimiters(String delimiters) {
	//		return this.setDelimiters(delimiters);
	//	}
	//	
	//	public String useDefaultDelimiters(String delimiters) {
	//		return this.useDefaultDelimiters(delimiters);
	//	}
	//	
	//	public void passData(PassData data) {
	//		this.passData(data);
	//	}
	//	
	//	public void displayResult(Display display) {
	//		this.displayResult(display);
	//	}

}