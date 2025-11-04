package computing;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import numberlettercountcomputing.ComputingApiImpl;
import numberlettercountcomputing.PassData;
import numberlettercountdatastoring.DataStoreApi;
import numberlettercountfetching.FetchApi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

public class TestComputingApi {

	@Test
	public void testInitializeAndCompute() {
		// Create mock dependencies
		FetchApi mockFetchApi = Mockito.mock(FetchApi.class);
		DataStoreApi mockDataStoreApi = Mockito.mock(DataStoreApi.class);

		// Create implementation with mocked dependencies
		ComputingApiImpl computingApi = new ComputingApiImpl();
		computingApi.setFetchApi(mockFetchApi);
		computingApi.setDataStoreApi(mockDataStoreApi);
		// Note: We'll need to add setter methods for dependencies

		//        // Test will fail because implementation returns failure value
		//        List<Integer> inputData = Arrays.asList(1, 2, 3);
		//        String result = computingApi.initalize(inputData);
		//        String result1 = "one,two,three";
		//        
		//        assertEquals(result,result1);
		// Test data
		List<Integer> inputData = Arrays.asList(1, 2, 3);
		String result = computingApi.initalize(inputData);
		String expected = "one,two,three";

		assertEquals(expected, result);



		// This test will fail as expected

	}

	@Test
	public void testInitializeWithEmptyList() {
		ComputingApiImpl computingApi = new ComputingApiImpl();
		List<Integer> inputData = Arrays.asList();
		String result = computingApi.initalize(inputData);

		assertEquals("", result);
	}

	@Test
	public void testInitializeWithNumbersOutOfRange() {
		ComputingApiImpl computingApi = new ComputingApiImpl();
		List<Integer> inputData = Arrays.asList(1, 15, 3); // 15 is out of 0-9 range
		String result = computingApi.initalize(inputData);
		String expected = "one,15,three";

		assertEquals(expected, result);
	}
}

