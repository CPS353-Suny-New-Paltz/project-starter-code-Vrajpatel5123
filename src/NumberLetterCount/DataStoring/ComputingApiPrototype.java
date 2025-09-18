package NumberLetterCount.DataStoring;

import java.util.ArrayList;
import java.util.List;

import NumberLetterCount.Fetching.PassData;
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
	
	public void sentData(SendInfo Sdata) {
		this.sentData(Sdata);
	}
	
	public void recieveData(RecieveInfo Rdata) {
		this.recieveData(Rdata);
	}
	
	public void extractData(Extract Edata) {
		this.extractData(Edata);
	}
	
	public void pass(PassData pdata) {
		this.pass(pdata);
	}
	
	public void process(ProcessData prdata) {
		this.process(prdata);
	}
	
	
	
	
	
	
	

}
