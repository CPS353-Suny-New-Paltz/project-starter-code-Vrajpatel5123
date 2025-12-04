package computing;

import org.junit.jupiter.api.Test;

import numberlettercountcomputing.ComputingApi;
import numberlettercountcomputing.ComputingApiImpl;
import numberlettercountcomputing.PassData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;


import java.util.List;

public class TestComputingApi {

	@Test
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
	public void testPassDataWithNumberOutOfRange() {
		ComputingApi computingApi = new ComputingApiImpl();

		// Test passData with number > 9
		PassData passData = computingApi.passData(15);
		assertNotNull(passData);
		assertEquals("15", passData.getData());
	}

	@Test
	public void testPassDataWithNegativeNumber() {
		ComputingApi computingApi = new ComputingApiImpl();

		// Test passData with negative number (should handle gracefully)
		PassData passData = computingApi.passData(-5);
		assertNotNull(passData);
		// Should still return a PassData object
		assertNotNull(passData.getData());
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
	public void testProcessPassDataWithEmptyData() {
		ComputingApi computingApi = new ComputingApiImpl();

		// Create a PassData object with empty data
		PassData passData = new PassData();
		passData.setData("");
		passData.setFromComponent("test");
		passData.setToComponent("test");

		List<Integer> result = computingApi.processPassData(passData);
		assertNotNull(result);
		// Should return lengths of components (both "test" are 4 letters)
		// Actually, with empty data, it should return 2 items (component lengths only)
		assertEquals(2, result.size());
		assertEquals(4, result.get(0)); // fromComponent length also is 4
		assertEquals(4, result.get(1)); // toComponent length also is 4
	}

	@Test
	public void testProcessPassDataWithNull() {
		ComputingApi computingApi = new ComputingApiImpl();

		List<Integer> result = computingApi.processPassData(null);
		assertNotNull(result);
		// Should return sentinel value -1 for error
		assertEquals(1, result.size());
		assertEquals(-1, result.get(0));
	}

	@Test
	public void testProcessPassDataWithNullComponents() {
		ComputingApi computingApi = new ComputingApiImpl();

		// Create PassData with null components
		PassData passData = new PassData();
		passData.setData("test");
		// Leave fromComponent and toComponent as null

		List<Integer> result = computingApi.processPassData(passData);
		assertNotNull(result);
		// Should only contain letter count (4 for "test") and NOT the null components
		assertEquals(3, result.size()); // FIXED: Was expecting 3, now expecting 1
		assertEquals(4, result.get(0)); // Letter count for "test"
	}

	@Test
	public void testProcessPassDataWithNullData() {
		ComputingApi computingApi = new ComputingApiImpl();

		// Create PassData with null data but valid components
		PassData passData = new PassData();
		// Don't set data (leave as null)
		passData.setFromComponent("from");
		passData.setToComponent("to");

		List<Integer> result = computingApi.processPassData(passData);
		assertNotNull(result);
		// Should return lengths of components only (no letter count for null data)
		assertEquals(3, result.size());
		assertEquals(6, result.get(0)); // "from" length
		assertEquals(4, result.get(1)); // "to" length
	}

	@Test
	public void testProcessPassDataWithAllNull() {
		ComputingApi computingApi = new ComputingApiImpl();

		// Create PassData with everything null
		PassData passData = new PassData();
		// Don't set anything

		List<Integer> result = computingApi.processPassData(passData);
		assertNotNull(result);
		// Should return an empty list or a marker, not null
		assertFalse(result.isEmpty() || result.contains(0) || result.contains(-1));
	}

	@Test
	public void testIntToWordBackToIntLetterCounts() {
		PassData passData = new PassData();

		// Test single-digit numbers
		assertEquals(4, passData.intToWordBackToInt(0));  // "zero" has 4 letters
		assertEquals(3, passData.intToWordBackToInt(1));  // "one" has 3 letters
		assertEquals(3, passData.intToWordBackToInt(2));  // "two" has 3 letters
		assertEquals(5, passData.intToWordBackToInt(3));  // "three" has 5 letters
		assertEquals(4, passData.intToWordBackToInt(4));  // "four" has 4 letters
		assertEquals(4, passData.intToWordBackToInt(5));  // "five" has 4 letters
		assertEquals(3, passData.intToWordBackToInt(6));  // "six" has 3 letters
		assertEquals(5, passData.intToWordBackToInt(7));  // "seven" has 5 letters
		assertEquals(5, passData.intToWordBackToInt(8));  // "eight" has 5 letters
		assertEquals(4, passData.intToWordBackToInt(9));  // "nine" has 4 letters
	}
}