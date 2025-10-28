package integration;

import org.junit.jupiter.api.Test;

import configuration.TestInputConfiguration;
import configuration.TestOutputConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import java.util.Arrays;
import java.util.List;

import numberlettercountcomputing.ComputingApiImpl;
import numberlettercountcomputing.PassData;
import numberlettercountfetching.Delimiters;
import numberlettercountfetching.Display;
import numberlettercountfetching.FetchApiImpl;
import numberlettercountfetching.FetchRequest;
import numberlettercountfetching.InputSource;
import numberlettercountfetching.ListFetchRequest;
import numberlettercountfetching.OutputResult;
import numberlettercountfetching.StoreFetch;
import inmemory.InMemoryDataStore;

public class ComputeEngineIntegrationTest {

	@Test
	public void testIntegrationWithInputData() {
		// Setup test configuration
		List<Integer> inputNumbers = Arrays.asList(1, 10, 25);
		TestInputConfiguration inputConfig = new TestInputConfiguration(inputNumbers);
		TestOutputConfiguration outputConfig = new TestOutputConfiguration();

		// Create in-memory data store
		InMemoryDataStore dataStore = new InMemoryDataStore(inputConfig, outputConfig);

		// Create computing API implementation
		ComputingApiImpl computingApi = new ComputingApiImpl();
		FetchApiImpl networkingApi = new FetchApiImpl();
		// Note: We'll need to configure the dataStoreApi dependency

		// Use the computing API to process input data
		PassData passDataObject = computingApi.passData(5);
		List<Integer> computedResult = computingApi.processPassData(passDataObject);
		// Integrate with networking API using the computed result
		// Create a FetchRequest with the computed data
		FetchRequest fetchRequest = new ListFetchRequest(computedResult);
		
//		int value = networkingApi.insertRequest(fetchRequest);
//		StoreFetch storeFetch = new StoreFetch(value);
//
//		// Pass the computed result to the networking API
//		int requestId = networkingApi.insertRequest(fetchRequest);
		
		List<Integer> requestId = networkingApi.insertRequest(fetchRequest);

		// Get the input source from networking API (using the correct method names)
		InputSource source = networkingApi.getInputSource();
		OutputResult output = networkingApi.getOutputResult();
		Delimiters delims = networkingApi.getDelimiters();

		// Also call the other methods available
		PassData passData = (PassData) networkingApi.inputSource();
		Display display = networkingApi.displayIt();

		// For now, directly use the data store to simulate the integration
		// This test will fail because we're not actually computing number-to-letter conversion
		dataStore.insertRequest(null);

		// Validate output - this will fail because we're just converting numbers to strings
		List<String> output1 = outputConfig.getOutputStrings();

		// Expected: ["one", "ten", "twenty-five"] but currently getting ["1", "10", "25"]
		assertFalse(output1.isEmpty(), "Output should not be empty");
		assertEquals(3, output1.size(), "Should have 3 output entries");

		// This assertion will fail until proper number-to-letter conversion is implemented as show in line 36 explaining how it should be.
		assertEquals("one", output1.get(0));
		assertEquals("ten", output1.get(1)); 
		assertEquals("twenty-five", output1.get(2));
	}
}
