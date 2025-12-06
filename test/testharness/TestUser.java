package testharness;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

public class TestUser {

	// TODO 3: Changed to your @NetworkAPI interface type
	private final numberlettercountfetching.FetchApi coordinator;

	public TestUser(numberlettercountfetching.FetchApi coordinator) {
		this.coordinator = coordinator;
	}

	public void run(String outputPath) {
		char delimiter = ';';
		String inputPath = "test" + File.separatorChar + "testInputFile.test";

		// TODO 4: Call appropriate method on coordinator
		try {
			// Read and parse input file
			List<String> lines = Files.readAllLines(new File(inputPath).toPath());
			if (lines.isEmpty()) {
				return;
			}

			// Parse numbers from file
			String[] numberStrings = lines.get(0).split(",");
			Integer[] numbers = new Integer[numberStrings.length];
			for (int i = 0; i < numberStrings.length; i++) {
				numbers[i] = Integer.parseInt(numberStrings[i].trim());
			}

			// Create request and process
			numberlettercountfetching.ListFetchRequest request = 
					new numberlettercountfetching.ListFetchRequest(Arrays.asList(numbers));

			List<Integer> results = coordinator.insertRequest(request);

			// Write results
			StringBuilder output = new StringBuilder();
			for (int i = 0; i < results.size(); i++) {
				if (i > 0) output.append(delimiter);
				output.append(results.get(i));
			}

			Files.write(new File(outputPath).toPath(), output.toString().getBytes());

		} catch (IOException | NumberFormatException e) {
			System.err.println("Error processing file: " + e.getMessage());
		}
	}
}
