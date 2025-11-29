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
		assertTrue(result.size() >= 2); // Should at least have component lengths
	}

	@Test
	public void testProcessPassDataWithNull() {
		ComputingApi computingApi = new ComputingApiImpl();

		List<Integer> result = computingApi.processPassData(null);
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}
}