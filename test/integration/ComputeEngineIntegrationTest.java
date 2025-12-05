
package integration;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import numberlettercountfetching.FetchApi;
import numberlettercountfetching.FetchApiImpl;
import numberlettercountfetching.FetchRequest;
import numberlettercountfetching.ListFetchRequest;
import numberlettercountfetching.IntFetchRequest;
import numberlettercountcomputing.ComputingApi;
import numberlettercountcomputing.ComputingApiImpl;
import numberlettercountcomputing.PassData;
import numberlettercountdatastoring.DataStoreApi;
import numberlettercountdatastoring.DataRequest;

// Import the InMemoryDataStore and its dependencies
import inmemory.InMemoryDataStore;
import configuration.TestInputConfiguration;
import configuration.TestOutputConfiguration;

public class ComputeEngineIntegrationTest {

	// Remove the setComputingApi call since InMemoryDataStore doesn't have it
	@Test
	public void testIntegrationWithFetchApiAndComputingApi() {
		// Create components
		FetchApi fetchApi = new FetchApiImpl();
		ComputingApi computingApi = new ComputingApiImpl();

		// Create InMemoryDataStore with test configurations
		TestInputConfiguration inputConfig = new TestInputConfiguration();
		TestOutputConfiguration outputConfig = new TestOutputConfiguration();
		DataStoreApi dataStoreApi = new InMemoryDataStore(inputConfig, outputConfig);

		// Connect components - InMemoryDataStore doesn't need ComputingApi
		((FetchApiImpl) fetchApi).setDataStoreApi(dataStoreApi);

		// Test FetchApi -> DataStoreApi integration
		FetchRequest listRequest = new ListFetchRequest(Arrays.asList(1, 2, 3));
		List<Integer> fetchResult = fetchApi.insertRequest(listRequest);

		// Test ComputingApi functionality
		PassData passData = computingApi.passData(5);
		List<Integer> processResult = computingApi.processPassData(passData);

		// Verify integration worked
		assertFalse(fetchResult.isEmpty(), "FetchApi should return data");
		assertFalse(processResult.isEmpty(), "ComputingApi should process data");

		// Test DataStoreApi validation
		assertTrue(dataStoreApi.validateNumber(5), "DataStoreApi should validate numbers");
		assertFalse(dataStoreApi.validateNumber(-1), "DataStoreApi should reject negative numbers");

		// Test DataStoreApi insert with valid data
		DataRequest dataRequest = new DataRequest(1, "integration_test", "10,20,30");
		int insertResult = dataStoreApi.insertRequest(dataRequest);
		// InMemoryDataStore returns 0 when configs are set
		assertEquals(0, insertResult, "InMemoryDataStore should return 0 for success");
	}

	@Test
	public void testFullWorkflow() {
		// Create and connect all components with InMemoryDataStore
		ComputingApi computingApi = new ComputingApiImpl();

		// Setup test configurations for InMemoryDataStore
		TestInputConfiguration inputConfig = new TestInputConfiguration();
		TestOutputConfiguration outputConfig = new TestOutputConfiguration();
		DataStoreApi dataStoreApi = new InMemoryDataStore(inputConfig, outputConfig);

		FetchApi fetchApi = new FetchApiImpl();
		((FetchApiImpl) fetchApi).setDataStoreApi(dataStoreApi);

		// Step 1: Fetch data
		FetchRequest fetchRequest = new IntFetchRequest(7);
		List<Integer> fetchedData = fetchApi.insertRequest(fetchRequest);

		// Step 2: Process data through computing API
		PassData passData = computingApi.passData(fetchedData.get(0));
		List<Integer> computedResults = computingApi.processPassData(passData);

		// Step 3: Store processed results - use valid numeric data format
		StringBuilder dataContent = new StringBuilder();
		for (int i = 0; i < computedResults.size(); i++) {
			if (i > 0) { 
				dataContent.append(",");
			}
			dataContent.append(computedResults.get(i));
		}

		DataRequest storeRequest = new DataRequest(2, "workflow_test", dataContent.toString());
		int storeResult = dataStoreApi.insertRequest(storeRequest);

		// Verify workflow success
		assertEquals(List.of(7), fetchedData);
		assertFalse(computedResults.isEmpty());
		// InMemoryDataStore returns 0 for success when input/output configs exist
		assertEquals(0, storeResult, "InMemoryDataStore should return 0 for success with valid configs");
		assertTrue(fetchApi.validateNumber(7));
		assertTrue(dataStoreApi.validateNumber(7));
	}

	@Test
	public void testDataStoreInsertWithValidData() {
		// Setup InMemoryDataStore with test configurations
		TestInputConfiguration inputConfig = new TestInputConfiguration();
		TestOutputConfiguration outputConfig = new TestOutputConfiguration();
		DataStoreApi dataStoreApi = new InMemoryDataStore(inputConfig, outputConfig);

		// Test with simple valid data that should definitely work
		DataRequest request = new DataRequest(1, "test", "1,2,3");
		int result = dataStoreApi.insertRequest(request);

		// InMemoryDataStore returns 0 when configs are set
		assertEquals(0, result, "InMemoryDataStore should return 0 with valid configurations");
	}

	@Test
	public void testComponentCommunication() {
		// Simple test to verify components can talk to each other
		// Setup InMemoryDataStore with test configurations
		TestInputConfiguration inputConfig = new TestInputConfiguration();
		TestOutputConfiguration outputConfig = new TestOutputConfiguration();
		DataStoreApi dataStoreApi = new InMemoryDataStore(inputConfig, outputConfig);

		FetchApi fetchApi = new FetchApiImpl();
		((FetchApiImpl) fetchApi).setDataStoreApi(dataStoreApi);

		ComputingApi computingApi = new ComputingApiImpl();

		// Test basic functionality without assuming specific return values
		FetchRequest request = new IntFetchRequest(5);
		List<Integer> fetchResult = fetchApi.insertRequest(request);

		assertFalse(fetchResult.isEmpty());
		assertEquals(5, fetchResult.get(0));

		// Test computing API
		PassData passData = computingApi.passData(5);
		assertTrue(passData.getData().contains("five"));

		// Test validation
		assertTrue(fetchApi.validateNumber(5));
		assertTrue(dataStoreApi.validateNumber(5));
	}
}
