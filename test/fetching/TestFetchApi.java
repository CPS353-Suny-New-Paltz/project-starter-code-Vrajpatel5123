package fetching;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.math.BigInteger;
import java.util.List;

import org.junit.jupiter.api.Test;

import numberlettercountfetching.FetchApiImpl;
import numberlettercountfetching.FetchRequest;
import numberlettercountfetching.IntFetchRequest;
import numberlettercountfetching.ListFetchRequest;
import numberlettercountfetching.StringFetchRequest;

import numberlettercountcomputing.ComputingApi;
import numberlettercountcomputing.ComputingApiImpl;
import numberlettercountdatastoring.DataStoreApi;
import numberlettercountdatastoring.DataStoreApiImpl;

public class TestFetchApi {

	// Helper method to create a properly configured FetchApi
	private FetchApiImpl createConfiguredFetchApi() {
		ComputingApi computingApi = new ComputingApiImpl();
		DataStoreApi dataStoreApi = new DataStoreApiImpl();
		FetchApiImpl fetchApi = new FetchApiImpl();
		fetchApi.setDataStoreApi(dataStoreApi);
		fetchApi.setComputingApi(computingApi);
		return fetchApi;
	}

	@Test
	public void testInsertRequestWithIntFetchRequest() {
		FetchApiImpl fetchApi = createConfiguredFetchApi();

		FetchRequest intRequest = new IntFetchRequest(123);
		List<BigInteger> result = fetchApi.insertRequest(intRequest);

		// Should return letter count for 123 as BigInteger
		assertEquals(1, result.size());
		assertTrue(result.get(0).compareTo(BigInteger.ZERO) > 0, "Should return positive letter count");
	}

	@Test
	public void testInsertRequestWithLargeIntFetchRequest() {
		FetchApiImpl fetchApi = createConfiguredFetchApi();

		BigInteger largeNumber = new BigInteger("123456789012345678901234567890");
		FetchRequest intRequest = new IntFetchRequest(largeNumber);
		List<BigInteger> result = fetchApi.insertRequest(intRequest);

		// Should handle large numbers
		assertEquals(1, result.size());
		// Result could be positive letter count or -1 for error
		assertTrue(result.get(0).compareTo(BigInteger.valueOf(-1)) != 0, "Should not return -1 for valid large number");
	}

	@Test
	public void testInsertRequestWithListFetchRequest() {
		FetchApiImpl fetchApi = createConfiguredFetchApi();

		List<BigInteger> numbers = List.of(
				BigInteger.valueOf(1), 
				BigInteger.valueOf(2), 
				BigInteger.valueOf(3)
				);
		FetchRequest listRequest = new ListFetchRequest(numbers);
		List<BigInteger> result = fetchApi.insertRequest(listRequest);

		assertEquals(3, result.size());
		for (BigInteger count : result) {
			assertTrue(count.compareTo(BigInteger.ZERO) > 0 || count.equals(BigInteger.valueOf(-1)),
					"Should return positive letter count or -1");
		}
	}

	@Test
	public void testInsertRequestWithStringFetchRequest() {
		FetchApiImpl fetchApi = createConfiguredFetchApi();

		FetchRequest stringRequest = new StringFetchRequest("abc");
		List<BigInteger> result = fetchApi.insertRequest(stringRequest);

		assertEquals(3, result.size());
		for (BigInteger count : result) {
			assertTrue(count.compareTo(BigInteger.ZERO) > 0 || count.equals(BigInteger.valueOf(-1)),
					"Should return positive letter count or -1");
		}
	}

	@Test
	public void testInsertRequestWithNull() {
		FetchApiImpl fetchApi = createConfiguredFetchApi();
		List<BigInteger> result = fetchApi.insertRequest(null);

		assertEquals(List.of(BigInteger.valueOf(-1)), result);
	}

	@Test
	public void testValidateNumber() {
		FetchApiImpl fetchApi = createConfiguredFetchApi();

		assertTrue(fetchApi.validateNumber(BigInteger.ZERO));
		assertTrue(fetchApi.validateNumber(BigInteger.valueOf(100)));
		assertTrue(fetchApi.validateNumber(new BigInteger("12345678901234567890")));
		assertFalse(fetchApi.validateNumber(BigInteger.valueOf(-1)));
		assertFalse(fetchApi.validateNumber(new BigInteger("-12345678901234567890")));
	}

	@Test
	public void testGetStoredData() {
		FetchApiImpl fetchApi = createConfiguredFetchApi();

		List<BigInteger> emptyData = fetchApi.getStoredData();
		assertTrue(emptyData.isEmpty());

		fetchApi.insertRequest(new IntFetchRequest(42));
		List<BigInteger> data = fetchApi.getStoredData();

		assertEquals(1, data.size());
		assertEquals(BigInteger.valueOf(42), data.get(0));
	}

	@Test
	public void testLetterCountsForSpecificNumbers() {
		FetchApiImpl fetchApi = createConfiguredFetchApi();

		// Test specific number conversions
		FetchRequest request1 = new IntFetchRequest(1);
		List<BigInteger> result1 = fetchApi.insertRequest(request1);
		assertEquals(1, result1.size());
		assertEquals(BigInteger.valueOf(3), result1.get(0)); // "one" has 3 letters

		FetchRequest request5 = new IntFetchRequest(5);
		List<BigInteger> result5 = fetchApi.insertRequest(request5);
		assertEquals(1, result5.size());
		assertEquals(BigInteger.valueOf(4), result5.get(0)); // "five" has 4 letters

		FetchRequest request10 = new IntFetchRequest(10);
		List<BigInteger> result10 = fetchApi.insertRequest(request10);
		assertEquals(1, result10.size());
		assertEquals(BigInteger.valueOf(3), result10.get(0)); // "ten" has 3 letters
	}

	@Test
	public void testFetchApiWithoutDependencies() {
		FetchApiImpl fetchApi = new FetchApiImpl(); // No dependencies set

		FetchRequest request = new IntFetchRequest(5);
		List<BigInteger> result = fetchApi.insertRequest(request);

		assertEquals(List.of(BigInteger.valueOf(-1)), result);
	}
}