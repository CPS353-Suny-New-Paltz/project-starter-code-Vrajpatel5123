package numberlettercountcomputing;

import java.util.ArrayList;
import java.util.List;


import project.annotations.ConceptualAPIPrototype;

public class ComputingApiPrototype {

	private List<Integer> data;
	private int currentData;

	@ConceptualAPIPrototype
	public void prototype(ComputingApi computeapi) {
		PassData result = computeapi.passData(5); // getting the return value.
		System.out.println(result.intToWordBackToInt());

	}

	// Every code goes inside prototype and not need shouldn't exist and only for when needed method should be inside prototype.


	//	public void initialize(int inputData) {
	//		this.initialize(inputData);
	//	}
	//	
	//	public List<Integer> compute(){
	//		return new ArrayList<>(currentData);
	//		//Just returns the same data
	//	}
	//	
	//	public void sentData(SendInfo sendData) {
	//		this.sentData(sendData);
	//	}
	//	
	//	public void recieveData(RecieveInfo recieveData) {
	//		this.recieveData(recieveData);
	//	}
	//	
	//	public void extractData(Extract extractData) {
	//		this.extractData(extractData);
	//	}
	//	
	//	public void pass(PassData passData) {
	//		this.pass(passData);
	//	}
	//	
	//	public void process(ProcessData processData) {
	//		this.process(processData);
	//	}








}
