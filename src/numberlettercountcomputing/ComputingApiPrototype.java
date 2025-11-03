package numberlettercountcomputing;

import java.util.ArrayList;
import java.util.List;


import project.annotations.ConceptualAPIPrototype;

public class ComputingApiPrototype {
	private List<Integer> data;
	private int currentData;

	@ConceptualAPIPrototype
	public void prototype(ComputeApi computeapi) {
		System.out.println("=== Starting Prototype Demo ===");
		computeapi.insertRequest();
		System.out.println("=== Prototype Demo Complete ===");
	}

	public void initialize(int inputData) {
		this.currentData = inputData;
		if (this.data == null) {
			this.data = new ArrayList<>();
		}
		this.data.add(inputData);

		// Use objects during initialization
		Extract extract = new Extract();
		extract.setSource("initialization");
		extract.setExtractedData("Number: " + inputData);
		System.out.println("Initialization extract: " + extract);
	}

	public List<Integer> compute(){
		// Use processData during computation
		ProcessData processData = new ProcessData();
		processData.setInputData(data != null ? data.toString() : "empty");
		processData.setProcessingType("prototype computation");

		System.out.println("Computing with process: " + processData);

		if (data == null) {
			return new ArrayList<>();
		}
		return new ArrayList<>(data);
	}

	public void sentData(SendInfo sendData) {
		// Actually use the sendData object
		System.out.println("Prototype sending data: " + sendData);
		if (sendData != null) {
			System.out.println("Data: " + sendData.getData() + " to: " 
					+ sendData.getDestination());
		}
	}

	public void recieveData(RecieveInfo recieveData) {
		// Actually use the recieveData object
		System.out.println("Prototype receiving data: " + recieveData);
		if (recieveData != null) {
			System.out.println("Data: " + recieveData.getData() + 
					" from: " + recieveData.getSource());
		}
	}

	public void extractData(Extract extractData) {
		// Actually use the extractData object
		System.out.println("Prototype extracting data: " + extractData);
		if (extractData != null) {
			System.out.println("From: " + extractData.getSource() + " method: "
					+ extractData.getExtractionMethod());
		}
	}

	public void pass(PassData passData) {
		// Actually use the passData object
		System.out.println("Prototype passing data: " + passData);
		if (passData != null) {
			System.out.println("From: " + passData.getFromComponent() + " to: " 
					+ passData.getToComponent());
		}
	}

	public void process(ProcessData processData) {
		// Actually use the processData object
		System.out.println("Prototype processing data: " + processData);
		if (processData != null) {
			System.out.println("Input: " + processData.getInputData() + " output: " 
					+ processData.getOutputData());
		}
	}
}
