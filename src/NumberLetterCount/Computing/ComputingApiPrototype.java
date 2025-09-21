package NumberLetterCount.Computing;

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
		this.initialize(inputData);
	}
	
	public List<Integer> compute(){
		return new ArrayList<>(currentData);
		//Just returns the same data
	}
	
	public void sentData(SendInfo sData) {
		this.sentData(sData);
	}
	
	public void recieveData(RecieveInfo rData) {
		this.recieveData(rData);
	}
	
	public void extractData(Extract eData) {
		this.extractData(eData);
	}
	
	public void pass(PassData pData) {
		this.pass(pData);
	}
	
	public void process(ProcessData prData) {
		this.process(prData);
	}
	
	
	
	
	
	
	

}
