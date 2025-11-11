// File: TestDataStoreApi.java (FIXED - simple tests only)
package datastoring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import numberlettercountcomputing.ComputingApi;
import numberlettercountcomputing.PassData;
import numberlettercountdatastoring.DataRequest;
import numberlettercountdatastoring.DataStoreApiImpl;

public class TestDataStoreApi {

	@Test
	public void testInsertRequest() {
		ComputingApi mockComputingApi = Mockito.mock(ComputingApi.class);
		Mockito.when(mockComputingApi.passData(Mockito.anyInt())).thenReturn(new PassData());
		Mockito.when(mockComputingApi.processPassData(Mockito.any(PassData.class))).thenReturn(List.of(1));

		DataStoreApiImpl dataStoreApi = new DataStoreApiImpl();
		dataStoreApi.setComputingApi(mockComputingApi);

		DataRequest request = new DataRequest(123, "test_source", "1,2");
		int result = dataStoreApi.insertRequest(request);

		assertEquals(0, result);
	}

	@Test
	public void testInsertRequestWithNull() {
		DataStoreApiImpl dataStoreApi = new DataStoreApiImpl();
		int result = dataStoreApi.insertRequest(null);
		assertEquals(-1, result);
	}

	@Test
	public void testFetchAllData() {
		DataStoreApiImpl dataStoreApi = new DataStoreApiImpl();
		DataRequest request = new DataRequest(1, "source", "10,20");
		dataStoreApi.insertRequest(request);

		List<Integer> result = dataStoreApi.fetchAllData();
		assertEquals(2, result.size());
	}

	@Test
	public void testValidateNumber() {
		DataStoreApiImpl dataStoreApi = new DataStoreApiImpl();
		assertTrue(dataStoreApi.validateNumber(5));
		assertFalse(dataStoreApi.validateNumber(-1));
	}

	@Test
	public void testProcessRequest() {
		DataStoreApiImpl dataStoreApi = new DataStoreApiImpl();
		dataStoreApi.insertRequest(new DataRequest(1, "source", "5"));
		boolean result = dataStoreApi.processRequest();
		assertTrue(result);
	}

	@Test
	public void testGetStoredDataCount() {
		DataStoreApiImpl dataStoreApi = new DataStoreApiImpl();
		dataStoreApi.insertRequest(new DataRequest(1, "source", "1,2,3"));
		assertEquals(3, dataStoreApi.getStoredDataCount());
	}

	@Test
	public void testClearStorage() {
		DataStoreApiImpl dataStoreApi = new DataStoreApiImpl();
		dataStoreApi.insertRequest(new DataRequest(1, "source", "1,2"));
		boolean cleared = dataStoreApi.clearStorage();
		assertTrue(cleared);
		assertEquals(0, dataStoreApi.getStoredDataCount());
	}
}