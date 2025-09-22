package numberlettercount;

import java.util.ArrayList;
import java.util.List;

import project.annotations.ConceptualAPIPrototype;

public class ComputingApiPrototype {
	
	private List<Integer> data;
	private int currentData;
	
	@ConceptualAPIPrototype
	public void prototype(ComputeApi computeapi) {
		computeapi.insertRequest();
	}
	
	
	public void initialize(int inputData) {
		this.currentData = currentData;
	}
	
	public List<Integer> compute(){
		return new ArrayList<>(currentData);
		//Just returns the same data
	}
	
	public void writeResult(String result, String delimiters) {
		
	}

}
