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
		// Note: We'll need to add setter methods for dependencies

		// Test will fail because implementation returns failure value
		List<Integer> inputData = Arrays.asList(1, 2, 3);
		PassData result = computingApi.passData(0);
		int result1 = 4;
		int checks =  result.intToWordBackToInt();


		assertEquals(result1,checks);



		// This test will fail as expected

	}
}

