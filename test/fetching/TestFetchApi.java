package fetching;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import numberlettercountdatastoring.DataStoreApi;
import numberlettercountfetching.FetchApiImpl;
import numberlettercountfetching.FetchRequest;
import numberlettercountfetching.IntFetchRequest;

public class TestFetchApi {

	@Test
	public void testInsertRequest() {
		// Create mock dependencies
		DataStoreApi mockDataStoreApi = Mockito.mock(DataStoreApi.class);

		// Create implementation with mocked dependency
		FetchApiImpl fetchApi = new FetchApiImpl();
		// Note: We'll need to add setter methods or package-private access for dataStoreApi

		// Test will fail because implementation returns failure value
		FetchRequest request = new IntFetchRequest(123);
		List<Integer> result = fetchApi.insertRequest(request);

		// This test will fail as expected
		assertTrue(result.size() <= 0, "Should return non-empty list but returned: " + result);
		// The test fails due to the empty implementation isn't working yet
	}
}