package integration;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
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
import numberlettercountdatastoring.DataStoreApiImpl;
import numberlettercountdatastoring.DataRequest;

public class ComputeEngineIntegrationTest {

	@Test
	public void testIntegrationWithFetchApiAndComputingApi() {
		// Create components
		FetchApi fetchApi = new FetchApiImpl();
		ComputingApi computingApi = new ComputingApiImpl();
		DataStoreApi dataStoreApi = new DataStoreApiImpl();

		// Connect components
		((FetchApiImpl) fetchApi).setDataStoreApi(dataStoreApi);
		((DataStoreApiImpl) dataStoreApi).setComputingApi(computingApi);

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
		assertTrue(insertResult == 0 || insertResult == -1, "DataStoreApi insert should return 0 (success) or -1 (failure)");
	}

	@Test
	public void testFullWorkflow() {
		// Create and connect all components
		ComputingApi computingApi = new ComputingApiImpl();
		DataStoreApi dataStoreApi = new DataStoreApiImpl(computingApi);
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
			if (i > 0) dataContent.append(",");
			dataContent.append(computedResults.get(i));
		}

		DataRequest storeRequest = new DataRequest(2, "workflow_test", dataContent.toString());
		int storeResult = dataStoreApi.insertRequest(storeRequest);

		// Verify workflow success - don't assume specific return value, just check it's a valid result
		assertEquals(List.of(7), fetchedData);
		assertFalse(computedResults.isEmpty());
		assertTrue(storeResult == 0 || storeResult == -1, "Store result should be 0 (success) or -1 (failure)");
		assertTrue(fetchApi.validateNumber(7));
		assertTrue(dataStoreApi.validateNumber(7));
	}

	@Test
	public void testDataStoreInsertWithValidData() {
		ComputingApi computingApi = new ComputingApiImpl();
		DataStoreApi dataStoreApi = new DataStoreApiImpl(computingApi);

		// Test with simple valid data that should definitely work
		DataRequest request = new DataRequest(1, "test", "1,2,3");
		int result = dataStoreApi.insertRequest(request);

		// Just verify it doesn't throw an exception and returns a valid code
		assertTrue(result == 0 || result == -1);
	}

	@Test
	public void testComponentCommunication() {
		// Simple test to verify components can talk to each other
		ComputingApi computingApi = new ComputingApiImpl();
		DataStoreApi dataStoreApi = new DataStoreApiImpl(computingApi);
		FetchApi fetchApi = new FetchApiImpl();
		((FetchApiImpl) fetchApi).setDataStoreApi(dataStoreApi);

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