package project.checkpointtests;

import numberlettercountfetching.CoordinatorAPI;
import numberlettercountfetching.CoordinatorApiImpl;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ManualTestingFramework {

	public static final String INPUT = "manualTestInput.txt";
	public static final String OUTPUT = "manualTestOutput.txt";

	public static void main(String[] args) throws IOException {
		System.out.println("=== Starting Manual Testing Framework ===");

		// 1. Create input file with test data
		Path inputPath = Paths.get(INPUT);
		Files.write(inputPath, "1\n2\n3".getBytes(),
				StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
		System.out.println("✓ Created input file: " + INPUT);

		// 2. Create Coordinator API (which internally creates all 3 APIs)
		CoordinatorAPI coordinator = new CoordinatorApiImpl();
		System.out.println("✓ Created Coordinator API with all 3 components");

		// 3. Process the file - ALL LOGIC IS INSIDE CoordinatorApiImpl!
		System.out.println("\n=== Processing File ===");
		boolean success = coordinator.processFile(INPUT, OUTPUT);

<<<<<<< Updated upstream
		// 4. Report results
		if (success) {
			System.out.println("\n✓ Successfully processed input file!");
			System.out.println("Input: " + INPUT);
			System.out.println("Output: " + OUTPUT);
			System.out.println("Results: " + coordinator.getLastResults());
		} else {
			System.out.println("\n✗ Failed to process input file");
		}
=======
		System.out.println("\n=== Running Computation ===");
		System.out.println("Input file: " + INPUT);
		System.out.println("Output file: " + OUTPUT);

		// ONLY fetchApi calls - no file I/O logic
		FetchRequest request = new FetchRequest();
		List<BigInteger> results = fetchApi.insertRequest(request);

		// Write results to output file (this is just for the test framework output)
		Path outputPath = Paths.get(OUTPUT);
		String outputLine = "completed";
		Files.write(outputPath, outputLine.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
		System.out.println("✓ Results written to: " + OUTPUT);
		System.out.println("Output content: " + outputLine);
		System.out.println("Fetch API results: " + results);
>>>>>>> Stashed changes

		System.out.println("\n=== Manual Testing Framework Finished ===");
	}
}