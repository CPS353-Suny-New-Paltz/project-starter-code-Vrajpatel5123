package fetching;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;



import numberlettercountfetching.FetchApiImpl;
import numberlettercountfetching.FetchRequest;
import numberlettercountfetching.IntFetchRequest;
import numberlettercountfetching.ListFetchRequest;
import numberlettercountfetching.StringFetchRequest;

public class TestFetchApi {

	@Test
	public void testInsertRequest() {
		// Create implementation
		FetchApiImpl fetchApi = new FetchApiImpl();

		// Test IntFetchRequest
		FetchRequest intRequest = new IntFetchRequest(123);
		List<Integer> intResult = fetchApi.insertRequest(intRequest);
		assertEquals(List.of(123), intResult);
		assertTrue(intResult.size() == 1);

		// Test ListFetchRequest
		FetchRequest listRequest = new ListFetchRequest(List.of(1, 2, 3));
		List<Integer> listResult = fetchApi.insertRequest(listRequest);
		assertEquals(List.of(1, 2, 3), listResult);
		assertTrue(listResult.size() == 3);

		// Test StringFetchRequest
		FetchRequest stringRequest = new StringFetchRequest("abc");
		List<Integer> stringResult = fetchApi.insertRequest(stringRequest);
		assertTrue(stringResult.size() > 0);
		assertTrue(stringResult.get(0) == 97);

		// Test fetchAllData
		List<Integer> allData = fetchApi.fetchAllData();
		assertTrue(allData.size() >= 4);

		// Test validateNumber
		assertTrue(fetchApi.validateNumber(5));
		assertTrue(fetchApi.validateNumber(0));
	}

	@Test
	public void testFetchAllData() {
		FetchApiImpl fetchApi = new FetchApiImpl();
		List<Integer> emptyData = fetchApi.fetchAllData();
		assertTrue(emptyData.isEmpty());

		fetchApi.insertRequest(new IntFetchRequest(42));
		List<Integer> data = fetchApi.fetchAllData();
		assertTrue(data.size() == 1);
		assertTrue(data.get(0) == 42);
	}

	@Test
	public void testValidateNumber() {
		FetchApiImpl fetchApi = new FetchApiImpl();
		assertTrue(fetchApi.validateNumber(0));
		assertTrue(fetchApi.validateNumber(100));
		assertTrue(fetchApi.validateNumber(999));
	}
}