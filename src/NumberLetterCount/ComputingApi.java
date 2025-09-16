package NumberLetterCount;

import java.util.List;

import project.annotations.ConceptualAPI;

@ConceptualAPI
public interface ComputingApi {
	
	int initalize(List<Integer> inputData);
	
	List<Integer> compute();
	
	String writeResult(String result, String delimiters);
	
}
