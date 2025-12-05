
package project.checkpointtests;

import numberlettercountcomputing.ComputingApi;
import numberlettercountcomputing.ComputingApiImpl;
import numberlettercountdatastoring.DataStoreApi;
import numberlettercountdatastoring.DataStoreApiImpl;
import numberlettercountfetching.FetchApi;
import numberlettercountfetching.FetchApiImpl;
import numberlettercountfetching.FetchRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class ManualTestingFramework {

	public static final String INPUT = "manualTestInput.txt";
	public static final String OUTPUT = "manualTestOutput.txt";

	public static void main(String[] args) throws IOException {
		System.out.println("=== Starting Manual Testing Framework ===");

		// Create the three API implementations
		FetchApi fetchApi = new FetchApiImpl();
		DataStoreApi dataStoreApi = new DataStoreApiImpl();
		ComputingApi computingApi = new ComputingApiImpl();

		((FetchApiImpl) fetchApi).setDataStoreApi(dataStoreApi);
		((DataStoreApiImpl) dataStoreApi).setComputingApi(computingApi);

		System.out.println("✓ All 3 APIs instantiated");

		System.out.println("\n=== Running Computation ===");
		System.out.println("Input file: " + INPUT);
		System.out.println("Output file: " + OUTPUT);

		// ONLY fetchApi calls - no file I/O logic
		FetchRequest request = new FetchRequest();
		List<Integer> results = fetchApi.insertRequest(request);

		// Write results to output file (this is just for the test framework output)
		Path outputPath = Paths.get(OUTPUT);
		String outputLine = "completed";
		Files.write(outputPath, outputLine.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
		System.out.println("✓ Results written to: " + OUTPUT);
		System.out.println("Output content: " + outputLine);
		System.out.println("Fetch API results: " + results);

		System.out.println("\n=== Manual Testing Framework Finished ===");
	}
<<<<<<< HEAD
}
=======

	// Simple helper method to convert numbers to words (0-9 only for simplicity)
	private static String convertNumberToWord(int number) {
		String[] numberWords = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};

		if (number >= 0 && number <= 9) {
			return numberWords[number];
		} else {
			// For numbers outside 0-9, just return the number as string
			return String.valueOf(number);
		}
	}
}
>>>>>>> main
