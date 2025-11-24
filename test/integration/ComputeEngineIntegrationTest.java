// File: ComputeEngineIntegrationTest.java (FIXED - removed unused variable)
package integration;

import org.junit.jupiter.api.Test;
import configuration.TestInputConfiguration;
import configuration.TestOutputConfiguration;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import numberlettercountfetching.FetchApiImpl;
import numberlettercountfetching.FetchRequest;
import numberlettercountfetching.ListFetchRequest;
import numberlettercountfetching.IntFetchRequest;
import numberlettercountcomputing.ComputingApiImpl;
import inmemory.InMemoryDataStore;

public class ComputeEngineIntegrationTest {

	@Test
	public void testIntegrationWithFetchApiAndComputingApi() {
		try {
			// Setup test configuration
			List<Integer> inputNumbers = Arrays.asList(1, 2, 3);
			TestInputConfiguration inputConfig = new TestInputConfiguration(inputNumbers);
			TestOutputConfiguration outputConfig = new TestOutputConfiguration();

			// Create components
			InMemoryDataStore dataStore = new InMemoryDataStore(inputConfig, outputConfig);
			FetchApiImpl fetchApi = new FetchApiImpl();
			ComputingApiImpl computingApi = new ComputingApiImpl();

			// Connect components
			fetchApi.setDataStoreApi(dataStore);
			computingApi.setFetchApi(fetchApi);
			computingApi.setDataStoreApi(dataStore);

			// Test FetchApi -> ComputingApi integration
			FetchRequest listRequest = new ListFetchRequest(inputNumbers);
			List<Integer> fetchResult = fetchApi.insertRequest(listRequest);

			// ComputingApi processes data from FetchApi
			String computeResult = computingApi.initalize(fetchResult);

			// Verify integration worked
			assertFalse(fetchResult.isEmpty(), "FetchApi should return data");
			assertFalse(computeResult.isEmpty(), "ComputingApi should process data");
			assertEquals("one,two,three", computeResult, "ComputingApi should convert numbers to words");

			// Test ComputingApi -> FetchApi integration
			FetchRequest intRequest = new IntFetchRequest(5);
			fetchApi.insertRequest(intRequest); // Removed unused variable

			// ComputingApi validates number using FetchApi
			boolean isValid = fetchApi.validateNumber(5);
			assertTrue(isValid, "FetchApi should validate numbers");

			// Test DataStore integration
			boolean processSuccess = dataStore.processRequest();
			assertTrue(processSuccess, "DataStore should process requests successfully");

			// Test full workflow: Fetch -> Compute -> Store
			List<Integer> allData = fetchApi.fetchAllData();
			String processedData = computingApi.initalize(allData);

			assertTrue(processedData.contains("one"), "Processed data should contain converted words");
		} catch(Exception e) {
			fail("Integration test should not throw exceptions: " + e.getMessage());
		}
	}
	@Test
	public void testErrorHandlingIntegration() {
		// Test error scenarios
		ComputingApiImpl computingApi = new ComputingApiImpl();

		// Test null input
		String result = computingApi.initalize(null);
		assertTrue(result.startsWith("ERROR"), "Should handle null input gracefully");

		// Test empty input
		String emptyResult = computingApi.initalize(Arrays.asList());
		assertTrue(emptyResult.startsWith("ERROR"), "Should handle empty input gracefully");

		// Test with null elements
		List<Integer> badList = Arrays.asList(1, null, 3);
		String badResult = computingApi.initalize(badList);
		assertTrue(badResult.startsWith("ERROR"), "Should handle null elements gracefully");
	}
}