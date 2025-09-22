package numberlettercount.fetching;

import project.annotations.NetworkAPIPrototype;

public class FetchApiPrototype {
	
	String inputSource;
	
	String outputResult;
	
	
	
	final String DEFAULT_DELIMITERS = ".;|";
	
	private String delimiters;

	@NetworkAPIPrototype
	public void prototype(FetchApi fetchapi)   {
		
		fetchapi.insertRequest(new FetchRequest(123));		
	}
	
	public  String getInputSource(String InputSource) {
		return inputSource;
	}
	
	public String getOutputResult(String outputResult) {
		return outputResult;
	}
	
	public String setDelimiters(String delimiters) {
		return this.setDelimiters(delimiters);
	}
	
	public void useDefaultDelimiters() {
		this.delimiters = DEFAULT_DELIMITERS;
	}
	
	public void passData(PassData data) {
		this.passData(data);
	}
	
	public void DisplayResult(Display display) {
		this.DisplayResult(display);
	}

}