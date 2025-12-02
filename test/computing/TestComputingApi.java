package computing;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import numberlettercountcomputing.ComputingApi;
import numberlettercountcomputing.ComputingApiImpl;
import numberlettercountcomputing.PassData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

public class TestComputingApi {

	@Test
<<<<<<< HEAD
	public void testInitializeAndCompute() {
		// Create mock dependencies
		FetchApi mockFetchApi = Mockito.mock(FetchApi.class);
		DataStoreApi mockDataStoreApi = Mockito.mock(DataStoreApi.class);

		// Create implementation with mocked dependencies
		ComputingApiImpl computingApi = new ComputingApiImpl();
		computingApi.setFetchApi(mockFetchApi);
		computingApi.setDataStoreApi(mockDataStoreApi);

		// Test data
		List<Integer> inputData = Arrays.asList(1, 2, 3);
		String result = computingApi.initalize(inputData);
		String expected = "one,two,three";

		assertEquals(expected, result);
	}

	@Test
	public void testInitializeWithEmptyList() {
		ComputingApiImpl computingApi = new ComputingApiImpl();
		List<Integer> inputData = Arrays.asList();
		String result = computingApi.initalize(inputData);

		// Updated: Now returns error message instead of empty string
		assertTrue(result.startsWith("ERROR: Input data cannot be empty"));
	}

	@Test
	public void testInitializeWithNullInput() {
		ComputingApiImpl computingApi = new ComputingApiImpl();
		String result = computingApi.initalize(null);

		// Updated: Now returns error message for null input
		assertTrue(result.startsWith("ERROR: Input data cannot be null"));
	}

	@Test
	public void testInitializeWithNumbersOutOfRange() {
		ComputingApiImpl computingApi = new ComputingApiImpl();
		List<Integer> inputData = Arrays.asList(1, 15, 3); // 15 is out of 0-9 range
		String result = computingApi.initalize(inputData);
		String expected = "one,15,three";

		assertEquals(expected, result);
	}

	@Test
	public void testInitializeWithNullElements() {
		ComputingApiImpl computingApi = new ComputingApiImpl();
		List<Integer> inputData = Arrays.asList(1, null, 3);
		String result = computingApi.initalize(inputData);

		// Updated: Now returns error message for null elements
		assertTrue(result.startsWith("ERROR: Input list contains null values"));
	}

	@Test
	public void testPassDataCreation() {
		ComputingApiImpl computingApi = new ComputingApiImpl();

		// Test passData() method - USING PassData import
		PassData passData = computingApi.passData();
		assertNotNull(passData);
		assertEquals("computing module", passData.getFromComponent());
		assertEquals("storage module", passData.getToComponent());
	}

	@Test
=======
>>>>>>> main
	public void testPassDataWithNumber() {
		ComputingApi computingApi = new ComputingApiImpl();

		// Test passData(int number) method
		PassData passData = computingApi.passData(5);
		assertNotNull(passData);
		assertEquals("five", passData.getData());
		assertEquals("number_converter", passData.getFromComponent());
		assertEquals("output_processor", passData.getToComponent());
	}

	@Test
<<<<<<< HEAD
	public void testPassDataWithNegativeNumber() {
		ComputingApiImpl computingApi = new ComputingApiImpl();

		// Test passData with negative number
		PassData passData = computingApi.passData(-5);
		assertNotNull(passData);
		// Should still create PassData but might have different data
		assertNotNull(passData.getData());
	}

	@Test
	public void testProcessPassData() {
		ComputingApiImpl computingApi = new ComputingApiImpl();
=======
	public void testPassDataWithNumberOutOfRange() {
		ComputingApi computingApi = new ComputingApiImpl();
>>>>>>> main

		// Test passData with number > 9
		PassData passData = computingApi.passData(15);
		assertNotNull(passData);
		assertEquals("15", passData.getData());
	}

	@Test
	public void testProcessPassData() {
		ComputingApi computingApi = new ComputingApiImpl();

		// Create a PassData object
		PassData passData = new PassData();
		passData.setData("test data");
		passData.setFromComponent("source");
		passData.setToComponent("destination");

		// Test processPassData method
		List<Integer> result = computingApi.processPassData(passData);
		assertNotNull(result);
		assertEquals(3, result.size());

		// Should contain letter count (8 for "test data") and lengths of from/to components
		assertEquals(8, result.get(0)); // "test data" has 8 letters (excluding space)
		assertEquals(6, result.get(1)); // "source" has 6 letters
		assertEquals(11, result.get(2)); // "destination" has 11 letters
	}

	@Test
<<<<<<< HEAD
	public void testProcessPassDataWithNull() {
		ComputingApiImpl computingApi = new ComputingApiImpl();

		// Test processPassData with null parameter
		List<Integer> result = computingApi.processPassData(null);
		assertNotNull(result);

		// Should return error indicator (-1)
		assertEquals(1, result.size());
		assertEquals(-1, result.get(0));
	}

	@Test
	public void testComputeMethod() {
		ComputingApiImpl computingApi = new ComputingApiImpl();
		List<Integer> result = computingApi.compute();
=======
	public void testProcessPassDataWithEmptyData() {
		ComputingApi computingApi = new ComputingApiImpl();
>>>>>>> main

		// Create a PassData object with empty data
		PassData passData = new PassData();
		passData.setData("");
		passData.setFromComponent("test");
		passData.setToComponent("test");

		List<Integer> result = computingApi.processPassData(passData);
		assertNotNull(result);
		assertTrue(result.size() >= 2); // Should at least have component lengths
	}

	@Test
	public void testProcessPassDataWithNull() {
		ComputingApi computingApi = new ComputingApiImpl();

		List<Integer> result = computingApi.processPassData(null);
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}

	@Test
	public void testWriteResultWithNullResult() {
		ComputingApiImpl computingApi = new ComputingApiImpl();
		String result = computingApi.writeResult(null, ",");

		// Updated: Now returns error message for null result
		assertTrue(result.startsWith("ERROR: Result cannot be null"));
	}

	@Test
	public void testWriteResultWithEmptyResult() {
		ComputingApiImpl computingApi = new ComputingApiImpl();
		String result = computingApi.writeResult("   ", ",");

		// Updated: Now returns error message for empty result
		assertTrue(result.startsWith("ERROR: Result cannot be empty"));
	}

	@Test
	public void testWriteResultWithNullDelimiters() {
		ComputingApiImpl computingApi = new ComputingApiImpl();
		String result = computingApi.writeResult("test data", null);

		// Should still work but use default delimiter
		assertTrue(result.contains("Processed: test data"));
		assertTrue(result.contains("with delimiters: ,"));
	}
}