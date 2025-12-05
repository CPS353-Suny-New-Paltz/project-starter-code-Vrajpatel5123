package numberlettercountfetching;

import numberlettercountcomputing.ComputingApi;
import numberlettercountcomputing.ComputingApiImpl;
import numberlettercountcomputing.PassData;
import numberlettercountdatastoring.DataStoreApi;
import numberlettercountdatastoring.DataStoreApiImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class CoordinatorApiImpl implements CoordinatorAPI {
	private final FetchApi fetchApi;
	private final DataStoreApi dataStoreApi;
	private final ComputingApi computingApi;
	private List<Integer> lastResults;

	public CoordinatorApiImpl() {
		this.fetchApi = new FetchApiImpl();
		this.dataStoreApi = new DataStoreApiImpl();
		this.computingApi = new ComputingApiImpl();

		// Wire dependencies
		((FetchApiImpl) fetchApi).setDataStoreApi(dataStoreApi);
		((DataStoreApiImpl) dataStoreApi).setComputingApi(computingApi);

		this.lastResults = new ArrayList<>();
	}


	public boolean processFile(String inputFile, String outputFile) {
		try {
			// 1. Read numbers from input file
			Path inputPath = Paths.get(inputFile);
			List<Integer> numbers = new ArrayList<>();

			if (!Files.exists(inputPath)) {
				System.err.println("Input file not found: " + inputFile);
				return false;
			}

			for (String line : Files.readAllLines(inputPath)) {
				try {
					numbers.add(Integer.parseInt(line.trim()));
				} catch (NumberFormatException e) {
					System.err.println("Skipping invalid line: " + line);
				}
			}

			if (numbers.isEmpty()) {
				System.err.println("No valid numbers in input file");
				return false;
			}

			System.out.println("Processing " + numbers.size() + " numbers: " + numbers);

			// 2. Process each number through ComputingApi
			List<Integer> letterCounts = new ArrayList<>();
			for (Integer number : numbers) {
				PassData passData = computingApi.passData(number);
				List<Integer> results = computingApi.processPassData(passData);
				if (!results.isEmpty()) {
					letterCounts.add(results.get(0)); // Letter count
				}
			}

			this.lastResults = letterCounts;

			// 3. Write results to output file
			StringBuilder output = new StringBuilder();
			for (int i = 0; i < letterCounts.size(); i++) {
				output.append(letterCounts.get(i));
				if (i < letterCounts.size() - 1) {
					output.append(",");
				}
			}

			Path outputPath = Paths.get(outputFile);
			Files.write(outputPath, output.toString().getBytes(),
					StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

			System.out.println("Successfully wrote " + letterCounts.size() + 
					" results to: " + outputFile);
			System.out.println("Output: " + output.toString());

			return true;

		} catch (IOException e) {
			System.err.println("Error processing file: " + e.getMessage());
			return false;
		}
	}


	public List<Integer> getLastResults() {
		return new ArrayList<>(lastResults); // Return defensive copy
	}
}