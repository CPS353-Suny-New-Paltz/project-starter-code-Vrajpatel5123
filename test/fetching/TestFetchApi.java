package fetching;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.Test;

import numberlettercountfetching.FetchApi;
import numberlettercountfetching.FetchApiImpl;
import numberlettercountfetching.FetchRequest;
import numberlettercountfetching.IntFetchRequest;
import numberlettercountfetching.ListFetchRequest;
import numberlettercountfetching.StringFetchRequest;

public class TestFetchApi {

	@Test
	public void testInsertRequestWithIntFetchRequest() {
		FetchApi fetchApi = new FetchApiImpl();

		FetchRequest intRequest = new IntFetchRequest(123);
		List<Integer> result = fetchApi.insertRequest(intRequest);

		assertEquals(List.of(123), result);
	}

	@Test
	public void testInsertRequestWithListFetchRequest() {
		FetchApi fetchApi = new FetchApiImpl();

		FetchRequest listRequest = new ListFetchRequest(List.of(1, 2, 3));
		List<Integer> result = fetchApi.insertRequest(listRequest);

		assertEquals(List.of(1, 2, 3), result);
	}

	@Test
	public void testInsertRequestWithStringFetchRequest() {
		FetchApi fetchApi = new FetchApiImpl();

		FetchRequest stringRequest = new StringFetchRequest("abc");
		List<Integer> result = fetchApi.insertRequest(stringRequest);

		assertTrue(result.size() == 3);
		assertEquals(97, result.get(0)); // 'a' ASCII
		assertEquals(98, result.get(1)); // 'b' ASCII  
		assertEquals(99, result.get(2)); // 'c' ASCII
	}

	@Test
	public void testInsertRequestWithNull() {
		FetchApi fetchApi = new FetchApiImpl();
		List<Integer> result = fetchApi.insertRequest(null);

		assertEquals(List.of(-1), result);
	}

	@Test
	public void testValidateNumber() {
		FetchApi fetchApi = new FetchApiImpl();

		assertTrue(fetchApi.validateNumber(0));
		assertTrue(fetchApi.validateNumber(100));
		assertTrue(fetchApi.validateNumber(999));
		assertFalse(fetchApi.validateNumber(-1));
		assertFalse(fetchApi.validateNumber(-100));
	}

	@Test
	public void testGetStoredData() {
		FetchApiImpl fetchApi = new FetchApiImpl();

		// Initially empty
		List<Integer> emptyData = fetchApi.getStoredData();
		assertTrue(emptyData.isEmpty());

		// After inserting data
		fetchApi.insertRequest(new IntFetchRequest(42));
		List<Integer> data = fetchApi.getStoredData();

		assertEquals(1, data.size());
		assertEquals(42, data.get(0));
	}

	@Test
	public void testGetStoredDataCount() {
		FetchApiImpl fetchApi = new FetchApiImpl();

		assertEquals(0, fetchApi.getStoredDataCount());

		fetchApi.insertRequest(new ListFetchRequest(List.of(1, 2, 3)));
		assertEquals(3, fetchApi.getStoredDataCount());
	}
}