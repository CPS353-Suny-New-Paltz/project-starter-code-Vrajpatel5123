package numberlettercountcomputing;

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
		this.currentData = inputData;
		if (this.data == null) {
			this.data = new ArrayList<>();
		}
		this.data.add(inputData);
	}
	public List<Integer> compute(){
		//return new ArrayList<>(currentData);
		//Just returns the same data
		//Side note for the code below:
		//It returns actual data instead of currentData
		if (data == null) {
			return new ArrayList<>();
		}
		return new ArrayList<>(data);
	}

	public void sentData(SendInfo sendData) {
		this.sentData(sendData);
	}

	public void recieveData(RecieveInfo recieveData) {
		this.recieveData(recieveData);
	}

	public void extractData(Extract extractData) {
		this.extractData(extractData);
	}

	public void pass(PassData passData) {
		this.pass(passData);
	}

	public void process(ProcessData processData) {
		this.process(processData);
	}








}
