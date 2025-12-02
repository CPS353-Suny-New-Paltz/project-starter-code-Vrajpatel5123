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
import numberlettercountdatastoring.DataStoreApi;
import numberlettercountdatastoring.DataStoreApiImpl;

public class TestDataStoreApi {

	@Test
	public void testInsertRequest() {
		ComputingApi mockComputingApi = Mockito.mock(ComputingApi.class);
		Mockito.when(mockComputingApi.passData(Mockito.anyInt())).thenReturn(new PassData());
		Mockito.when(mockComputingApi.processPassData(Mockito.any(PassData.class))).thenReturn(List.of(1));

		DataStoreApi dataStoreApi = new DataStoreApiImpl(mockComputingApi);

		DataRequest request = new DataRequest(123, "test_source", "1,2");
		int result = dataStoreApi.insertRequest(request);

		assertEquals(0, result);
	}

	@Test
	public void testInsertRequestWithNull() {
		DataStoreApi dataStoreApi = new DataStoreApiImpl();
		int result = dataStoreApi.insertRequest(null);
		assertEquals(-1, result);
	}

	@Test
	public void testInsertRequestWithInvalidData() {
		DataStoreApi dataStoreApi = new DataStoreApiImpl();
		DataRequest request = new DataRequest(123, "test_source", "invalid,data");
		int result = dataStoreApi.insertRequest(request);
		assertEquals(-1, result);
	}

	@Test
	public void testValidateNumber() {
		DataStoreApi dataStoreApi = new DataStoreApiImpl();
		assertTrue(dataStoreApi.validateNumber(5));
		assertTrue(dataStoreApi.validateNumber(0));
		assertFalse(dataStoreApi.validateNumber(-1));
	}

	@Test
	public void testValidateNumberWithComputingApi() {
		ComputingApi mockComputingApi = Mockito.mock(ComputingApi.class);
		Mockito.when(mockComputingApi.passData(Mockito.anyInt())).thenReturn(new PassData());
		Mockito.when(mockComputingApi.processPassData(Mockito.any(PassData.class))).thenReturn(List.of(1));

		DataStoreApi dataStoreApi = new DataStoreApiImpl(mockComputingApi);
		assertTrue(dataStoreApi.validateNumber(10));
	}

	@Test
	public void testFetchAllData() {
		DataStoreApi dataStoreApi = new DataStoreApiImpl();
		DataRequest request = new DataRequest(1, "source", "10,20");
		dataStoreApi.insertRequest(request);

		List<Integer> result = ((DataStoreApiImpl) dataStoreApi).fetchAllData();
		assertEquals(2, result.size());
		assertTrue(result.contains(10));
		assertTrue(result.contains(20));
	}

	@Test
	public void testGetStoredDataCount() {
		DataStoreApiImpl dataStoreApi = new DataStoreApiImpl();
		dataStoreApi.insertRequest(new DataRequest(1, "source", "1,2,3"));
		assertEquals(3, dataStoreApi.getStoredDataCount());
	}
}