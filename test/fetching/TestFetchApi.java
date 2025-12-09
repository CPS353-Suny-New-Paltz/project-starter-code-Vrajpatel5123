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

// Need these for setting up dependencies
import numberlettercountcomputing.ComputingApi;
import numberlettercountcomputing.ComputingApiImpl;
import numberlettercountdatastoring.DataStoreApi;
import numberlettercountdatastoring.DataStoreApiImpl;

public class TestFetchApi {

	// Helper method to create a properly configured FetchApi
	private FetchApiImpl createConfiguredFetchApi() {
		ComputingApi computingApi = new ComputingApiImpl();
		DataStoreApi dataStoreApi = new DataStoreApiImpl(computingApi);
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

<<<<<<< Updated upstream
		// Should return letter count for 123, not 123
		// 123 = "one hundred twenty-three" = letters count
		assertEquals(1, result.size()); // One result
		assertTrue(result.get(0) > 0, "Should return positive letter count");
=======
		assertEquals(List.of(BigInteger.valueOf(123)), result);
	}

	@Test
	public void testInsertRequestWithLargeIntFetchRequest() {
		FetchApi fetchApi = new FetchApiImpl();

		BigInteger largeNumber = new BigInteger("123456789012345678901234567890");
		FetchRequest largeIntRequest = new IntFetchRequest(largeNumber);
		List<BigInteger> result = fetchApi.insertRequest(largeIntRequest);

		assertEquals(List.of(largeNumber), result);
		assertEquals(largeNumber.toString(), "123456789012345678901234567890");
>>>>>>> Stashed changes
	}

	@Test
	public void testInsertRequestWithListFetchRequest() {
		FetchApiImpl fetchApi = createConfiguredFetchApi();

		FetchRequest listRequest = new ListFetchRequest(List.of(
				BigInteger.valueOf(1), 
				BigInteger.valueOf(2), 
				BigInteger.valueOf(3)
				));
		List<BigInteger> result = fetchApi.insertRequest(listRequest);

<<<<<<< Updated upstream
		// Should return letter counts for [1, 2, 3], not [1, 2, 3]
		// 1 = "one" = 3 letters, 2 = "two" = 3 letters, 3 = "three" = 5 letters
		assertEquals(3, result.size());
		// All should be positive letter counts
		for (int count : result) {
			assertTrue(count > 0, "Should return positive letter count: " + count);
		}
=======
		assertEquals(List.of(
				BigInteger.valueOf(1), 
				BigInteger.valueOf(2), 
				BigInteger.valueOf(3)
				), result);
>>>>>>> Stashed changes
	}

	@Test
	public void testInsertRequestWithStringFetchRequest() {
		FetchApiImpl fetchApi = createConfiguredFetchApi();

		FetchRequest stringRequest = new StringFetchRequest("abc");
		List<BigInteger> result = fetchApi.insertRequest(stringRequest);

<<<<<<< Updated upstream
		// StringFetchRequest converts "abc" to ASCII: [97, 98, 99]
		// These are then processed as numbers through ComputingApi
		// 97 = "ninety-seven" = letter count, etc.
		assertEquals(3, result.size());
		// All should be positive letter counts or -1 if invalid
		for (int i = 0; i < result.size(); i++) {
			int count = result.get(i);
			// Could be letter count (positive) or -1 (if ASCII value is invalid)
			assertTrue(count > 0 || count == -1, 
					"Result " + i + " should be positive letter count or -1, got: " + count);
		}
=======
		assertTrue(result.size() == 3);
		assertEquals(BigInteger.valueOf(97), result.get(0)); // 'a' ASCII
		assertEquals(BigInteger.valueOf(98), result.get(1)); // 'b' ASCII  
		assertEquals(BigInteger.valueOf(99), result.get(2)); // 'c' ASCII
>>>>>>> Stashed changes
	}

	@Test
	public void testInsertRequestWithNull() {
<<<<<<< Updated upstream
		FetchApiImpl fetchApi = createConfiguredFetchApi();
		List<Integer> result = fetchApi.insertRequest(null);
=======
		FetchApi fetchApi = new FetchApiImpl();
		List<BigInteger> result = fetchApi.insertRequest(null);
>>>>>>> Stashed changes

		assertEquals(List.of(BigInteger.valueOf(-1)), result);
	}

	@Test
	public void testValidateNumber() {
		FetchApiImpl fetchApi = createConfiguredFetchApi();

		assertTrue(fetchApi.validateNumber(BigInteger.ZERO));
		assertTrue(fetchApi.validateNumber(BigInteger.valueOf(100)));
		assertTrue(fetchApi.validateNumber(BigInteger.valueOf(999)));
		assertFalse(fetchApi.validateNumber(BigInteger.valueOf(-1)));
		assertFalse(fetchApi.validateNumber(BigInteger.valueOf(-100)));
	}

	@Test
	public void testValidateLargeNumber() {
		FetchApi fetchApi = new FetchApiImpl();

		// Test with very large numbers
		BigInteger largeNumber = new BigInteger("99999999999999999999999999999999999999");
		assertTrue(fetchApi.validateNumber(largeNumber), "Should validate large positive numbers");

		BigInteger veryLargeNumber = new BigInteger("1".repeat(500)); // 500-digit number
		assertTrue(fetchApi.validateNumber(veryLargeNumber), "Should validate 500-digit numbers");

		// Test with extremely large numbers (beyond typical limits)
		BigInteger extremelyLarge = new BigInteger("10").pow(1000);
		boolean result = fetchApi.validateNumber(extremelyLarge);
		// This might be false if validation has size limits
		System.out.println("Validation for 10^1000: " + result);
	}

	@Test
	public void testGetStoredData() {
		FetchApiImpl fetchApi = createConfiguredFetchApi();

		// Initially empty
		List<BigInteger> emptyData = fetchApi.getStoredData();
		assertTrue(emptyData.isEmpty());

		// After inserting data - store original numbers
		fetchApi.insertRequest(new IntFetchRequest(42));
		List<BigInteger> data = fetchApi.getStoredData();

		assertEquals(1, data.size());
<<<<<<< Updated upstream
		assertEquals(42, data.get(0)); // getStoredData returns original numbers
=======
		assertEquals(BigInteger.valueOf(42), data.get(0));
>>>>>>> Stashed changes
	}

	@Test
	public void testGetStoredDataCount() {
		FetchApiImpl fetchApi = createConfiguredFetchApi();

		assertEquals(0, fetchApi.getStoredDataCount());

		fetchApi.insertRequest(new ListFetchRequest(List.of(
				BigInteger.valueOf(1), 
				BigInteger.valueOf(2), 
				BigInteger.valueOf(3)
				)));
		assertEquals(3, fetchApi.getStoredDataCount());
	}

	@Test
<<<<<<< Updated upstream
	public void testLetterCountsForSpecificNumbers() {
		FetchApiImpl fetchApi = createConfiguredFetchApi();

		// Test specific number conversions
		// 1 = "one" = 3 letters
		FetchRequest request1 = new IntFetchRequest(1);
		List<Integer> result1 = fetchApi.insertRequest(request1);
		assertEquals(1, result1.size());
		assertEquals(3, result1.get(0)); // "one" has 3 letters

		// 5 = "five" = 4 letters
		FetchRequest request5 = new IntFetchRequest(5);
		List<Integer> result5 = fetchApi.insertRequest(request5);
		assertEquals(1, result5.size());
		assertEquals(4, result5.get(0)); // "five" has 4 letters

		// 10 = "ten" = 3 letters
		FetchRequest request10 = new IntFetchRequest(10);
		List<Integer> result10 = fetchApi.insertRequest(request10);
		assertEquals(1, result10.size());
		assertEquals(3, result10.get(0)); // "ten" has 3 letters
	}

	@Test
	public void testFetchApiWithoutDependencies() {
		// Test that FetchApi returns -1 when dependencies aren't set
		FetchApiImpl fetchApi = new FetchApiImpl(); // No dependencies set

		FetchRequest request = new IntFetchRequest(5);
		List<Integer> result = fetchApi.insertRequest(request);

		// Should return -1 because no ComputingApi to get letter counts
		assertEquals(List.of(-1), result);
=======
	public void testGetTotalSum() {
		FetchApiImpl fetchApi = new FetchApiImpl();

		// Add some numbers
		fetchApi.insertRequest(new ListFetchRequest(List.of(
				BigInteger.valueOf(10), 
				BigInteger.valueOf(20), 
				BigInteger.valueOf(30)
				)));

		BigInteger total = fetchApi.getTotalSum();
		assertEquals(BigInteger.valueOf(60), total);
	}

	@Test
	public void testGetTotalSumWithLargeNumbers() {
		FetchApiImpl fetchApi = new FetchApiImpl();

		BigInteger large1 = new BigInteger("12345678901234567890");
		BigInteger large2 = new BigInteger("98765432109876543210");

		fetchApi.insertRequest(new ListFetchRequest(List.of(large1, large2)));

		BigInteger total = fetchApi.getTotalSum();
		BigInteger expected = large1.add(large2);
		assertEquals(expected, total);
	}

	@Test
	public void testInsertBatch() {
		FetchApiImpl fetchApi = new FetchApiImpl();

		// Create a large batch of numbers
		List<BigInteger> batch = List.of(
				BigInteger.valueOf(1), BigInteger.valueOf(2), BigInteger.valueOf(3),
				BigInteger.valueOf(4), BigInteger.valueOf(5), BigInteger.valueOf(6),
				BigInteger.valueOf(7), BigInteger.valueOf(8), BigInteger.valueOf(9),
				BigInteger.valueOf(10)
				);

		fetchApi.insertBatch(batch);

		assertEquals(10, fetchApi.getStoredDataCount());
		assertEquals(BigInteger.valueOf(55), fetchApi.getTotalSum());
	}

	@Test
	public void testClearStoredData() {
		FetchApiImpl fetchApi = new FetchApiImpl();

		// Add data
		fetchApi.insertRequest(new ListFetchRequest(List.of(
				BigInteger.valueOf(1), 
				BigInteger.valueOf(2), 
				BigInteger.valueOf(3)
				)));

		assertEquals(3, fetchApi.getStoredDataCount());

		// Clear data
		fetchApi.clearStoredData();

		assertEquals(0, fetchApi.getStoredDataCount());
		assertTrue(fetchApi.getStoredData().isEmpty());
	}

	@Test
	public void testAddNumberFromString() {
		FetchRequest request = new FetchRequest();

		request.addNumberFromString("12345678901234567890");
		request.addNumberFromString("99999999999999999999");

		assertEquals(2, request.size());
		assertEquals(new BigInteger("12345678901234567890"), request.getData().get(0));
		assertEquals(new BigInteger("99999999999999999999"), request.getData().get(1));
	}

	@Test
	public void testInvalidNumberString() {
		FetchRequest request = new FetchRequest();

		// Invalid number strings should not be added
		request.addNumberFromString("not-a-number");
		request.addNumberFromString("123abc");

		assertEquals(0, request.size());
		assertTrue(request.isEmpty());
>>>>>>> Stashed changes
	}
}