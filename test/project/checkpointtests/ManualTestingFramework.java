package project.checkpointtests;

//import edu.softwareeng.sample.ComputationCoordinator;
//import edu.softwareeng.sample.ComputeEngine;
//import edu.softwareeng.sample.ComputeEngineImpl;
//import edu.softwareeng.sample.ComputeRequest;
//import edu.softwareeng.sample.CoordinatorImpl;
//import edu.softwareeng.sample.DataStore;
//import edu.softwareeng.sample.DataStoreImpl;
//import edu.softwareeng.sample.FileInputConfig;
//import edu.softwareeng.sample.FileOutputConfig;

import numberlettercountcomputing.ComputingApi;
import numberlettercountcomputing.ComputingApiImpl;
//import numberlettercountcomputing.PassData;
import numberlettercountdatastoring.DataStoreApi;
import numberlettercountdatastoring.DataStoreApiImpl;
import numberlettercountfetching.FetchApi;
import numberlettercountfetching.FetchApiImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class ManualTestingFramework {

	public static final String INPUT = "manualTestInput.txt";
	public static final String OUTPUT = "manualTestOutput.txt";

	public static void main(String[] args) {
		System.out.println("=== Starting Manual Testing Framework ===");

		// Create the three API implementations
		FetchApi fetchApi = new FetchApiImpl();
		DataStoreApi dataStoreApi = new DataStoreApiImpl();
		ComputingApi computingApi = new ComputingApiImpl();

		// Set up minimal dependencies to avoid circular references
		((FetchApiImpl) fetchApi).setDataStoreApi(dataStoreApi);
		((DataStoreApiImpl) dataStoreApi).setComputingApi(computingApi);

		System.out.println("✓ All 3 APIs instantiated");

		System.out.println("\n=== Running Computation ===");
		System.out.println("Input file: " + INPUT);
		System.out.println("Output file: " + OUTPUT);
		System.out.println("Delimiter: ','");

		try {
			// Read input from file
			Path inputPath = Paths.get(INPUT);
			List<String> inputLines = Files.readAllLines(inputPath);
			System.out.println("Read input: " + inputLines);

			// Process each number from input
			List<String> outputResults = new ArrayList<>();

			for (String line : inputLines) {
				try {
					int number = Integer.parseInt(line.trim());

					// Validate number
					boolean isValid = fetchApi.validateNumber(number);
					System.out.println("Number " + number + " validation: " + isValid);

					if (isValid) {
						// Convert number to word representation
						String word = convertNumberToWord(number);

						// Count letters in the word
						int letterCount = word.length();

						// Add to results
						outputResults.add(String.valueOf(letterCount));
						System.out.println("Processed number " + number + " -> '" + word + "' -> " + letterCount + " letters");
					}
				} catch (NumberFormatException e) {
					System.out.println("Skipping invalid number: " + line);
				}
			}

			// Write results to output file as comma-separated values

			// Create a Path object pointing to the output file location
			Path outputPath = Paths.get(OUTPUT);
			// Check if the results list is not empty
			if (!outputResults.isEmpty()) {
				// Join all results with commas on a single line
				// Combine all results into one string separated by commas
				String outputLine = String.join(",", outputResults);
				// Write the combined string to the output file
				// StandardOpenOption.CREATE	Create the file if it doesn't exist
				// StandardOpenOption.TRUNCATE_EXISTING  Empty the file if it already exists
				Files.write(outputPath, outputLine.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
				// Print success message showing file location
				System.out.println("✓ Results written to: " + OUTPUT);

				// Print what was actually written to the file
				System.out.println("Output content: " + outputLine);
			} else {
				Files.write(outputPath, "No valid results".getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
				System.out.println("✓ No valid results written to: " + OUTPUT);
			}

		} catch (IOException e) {
			System.err.println("Error processing files: " + e.getMessage());
			e.printStackTrace();
		}

		System.out.println("\n=== Manual Testing Framework Finished ===");
	}

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