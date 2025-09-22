package numberlettercount.fetching;


import project.annotations.NetworkAPI;

@NetworkAPI
public interface FetchApi {
	
	int insertRequest(FetchRequest fetchRequest);
	
	InputSource getInputSource();
	
	OutputResult getOutputResult();
	
	Delimiters getDelimiters();
	
	PassData inputSource();
	
	Display displayIt();
	 
	
//	String setInputSource(String inputSource);
//	
//	String setOutputResult(String outputResult);
//	
//	String setDelimiters(String delimiters);
	
	//String useDefaultDelimiters();

}