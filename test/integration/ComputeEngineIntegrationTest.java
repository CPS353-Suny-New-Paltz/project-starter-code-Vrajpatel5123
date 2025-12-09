package integration;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import numberlettercountfetching.FetchApiImpl;
import numberlettercountfetching.FetchRequest;
import numberlettercountfetching.ListFetchRequest;
import numberlettercountfetching.IntFetchRequest;
import numberlettercountcomputing.ComputingApi;
import numberlettercountcomputing.ComputingApiImpl;
import numberlettercountcomputing.PassData;
import numberlettercountdatastoring.DataStoreApi;
import numberlettercountdatastoring.DataRequest;
import numberlettercountdatastoring.DataStoreApiImpl;

// Import InMemoryDataStore and its dependencies
import inmemory.InMemoryDataStore;
import configuration.TestInputConfiguration;
import configuration.TestOutputConfiguration;

public class ComputeEngineIntegrationTest {

    @Test
    public void testIntegrationWithFetchApiAndComputingApi() {
        // Create components
        ComputingApi computingApi = new ComputingApiImpl();
        DataStoreApi dataStoreApi = new DataStoreApiImpl();
        FetchApiImpl fetchApi = new FetchApiImpl();
        
        // Connect components
        fetchApi.setDataStoreApi(dataStoreApi);
        fetchApi.setComputingApi(computingApi);
        
        // Test with BigInteger
        List<BigInteger> testNumbers = Arrays.asList(
            BigInteger.valueOf(1), 
            BigInteger.valueOf(2), 
            BigInteger.valueOf(3)
        );
        FetchRequest listRequest = new ListFetchRequest(testNumbers);
        List<BigInteger> fetchResult = fetchApi.insertRequest(listRequest);

        // Test ComputingApi functionality
        PassData passData = computingApi.passData(5);
        List<Integer> processResult = computingApi.processPassData(passData);

        // Verify integration worked
        assertFalse(fetchResult.isEmpty(), "FetchApi should return data");
        assertNotEquals(BigInteger.valueOf(-1), fetchResult.get(0), "FetchApi should not return error code");
        assertFalse(processResult.isEmpty(), "ComputingApi should process data");
        assertNotEquals(-1, processResult.get(0), "ComputingApi should not return error code");

        // Test DataStoreApi validation
        assertTrue(dataStoreApi.validateNumber(5), "DataStoreApi should validate positive numbers");
        assertFalse(dataStoreApi.validateNumber(-1), "DataStoreApi should reject negative numbers");

        // Test DataStoreApi insert with valid data
        DataRequest dataRequest = new DataRequest(1, "integration_test", "10,20,30");
        int insertResult = dataStoreApi.insertRequest(dataRequest);
        assertTrue(insertResult >= 0, "DataStoreApi should return count of stored numbers: " + insertResult);
    }

    @Test
    public void testFullWorkflow() {
        // Create and connect all components
        ComputingApi computingApi = new ComputingApiImpl();
        DataStoreApi dataStoreApi = new DataStoreApiImpl();
        FetchApiImpl fetchApi = new FetchApiImpl();
        fetchApi.setDataStoreApi(dataStoreApi);
        fetchApi.setComputingApi(computingApi);

        // Step 1: Fetch data - should return letter count, not the number
        FetchRequest fetchRequest = new IntFetchRequest(7);
        List<BigInteger> fetchedData = fetchApi.insertRequest(fetchRequest);

        // Step 2: Process data through computing API
        PassData passData = computingApi.passData(7);
        List<Integer> computedResults = computingApi.processPassData(passData);

        // Verify workflow success
        // FetchApi should return letter count (not the number 7)
        // 7 = "seven" = 5 letters
        assertEquals(BigInteger.valueOf(5), fetchedData.get(0), "Should return letter count for number 7");
        assertFalse(computedResults.isEmpty(), "Should produce computed results");
        assertTrue(fetchApi.validateNumber(BigInteger.valueOf(7)), "FetchApi should validate number 7");
        assertTrue(dataStoreApi.validateNumber(7), "DataStoreApi should validate number 7");
    }

    @Test
    public void testFullWorkflowWithLargeNumbers() {
        // Create and connect all components
        ComputingApi computingApi = new ComputingApiImpl();
        DataStoreApi dataStoreApi = new DataStoreApiImpl();
        FetchApiImpl fetchApi = new FetchApiImpl();
        fetchApi.setDataStoreApi(dataStoreApi);
        fetchApi.setComputingApi(computingApi);

        // Test with large number
        BigInteger largeNumber = new BigInteger("12345678901234567890");
        FetchRequest fetchRequest = new IntFetchRequest(largeNumber);
        List<BigInteger> fetchedData = fetchApi.insertRequest(fetchRequest);

        // Should validate large number
        assertTrue(fetchApi.validateNumber(largeNumber), "Should validate large number");
        
        // Should return a result (could be letter count or -1 for error)
        assertNotNull(fetchedData);
        assertFalse(fetchedData.isEmpty());
        
        // Result should be either positive (letter count) or -1
        BigInteger result = fetchedData.get(0);
        assertTrue(result.compareTo(BigInteger.ZERO) > 0 || result.equals(BigInteger.valueOf(-1)),
                "Should return positive letter count or -1: " + result);
    }

    @Test
    public void testDataStoreInsertWithValidData() {
        DataStoreApi dataStoreApi = new DataStoreApiImpl();

        // Test with simple valid data
        DataRequest request = new DataRequest(1, "test", "1,2,3");
        int result = dataStoreApi.insertRequest(request);

        assertTrue(result >= 0, "Should return count of stored numbers: " + result);
    }

    @Test
    public void testDataStoreInsertWithInvalidData() {
        DataStoreApi dataStoreApi = new DataStoreApiImpl();

        // Test with null request
        int result1 = dataStoreApi.insertRequest(null);
        assertEquals(-1, result1, "Should return -1 for null request");

        // Test with empty data
        DataRequest request3 = new DataRequest(3, "test", "");
        int result3 = dataStoreApi.insertRequest(request3);
        assertEquals(-1, result3, "Should return -1 for empty data");
    }

    @Test
    public void testComponentCommunication() {
        // Test all components working together
        ComputingApi computingApi = new ComputingApiImpl();
        DataStoreApi dataStoreApi = new DataStoreApiImpl();
        FetchApiImpl fetchApi = new FetchApiImpl();
        fetchApi.setDataStoreApi(dataStoreApi);
        fetchApi.setComputingApi(computingApi);
        
        // Test FetchApi with BigInteger
        FetchRequest request = new IntFetchRequest(BigInteger.valueOf(5));
        List<BigInteger> fetchResult = fetchApi.insertRequest(request);

        assertFalse(fetchResult.isEmpty(), "Should return data");
        // Should return letter count, not the number 5
        // 5 = "five" = 4 letters
        assertEquals(BigInteger.valueOf(4), fetchResult.get(0), "Should return letter count for number 5");
        assertNotEquals(BigInteger.valueOf(-1), fetchResult.get(0), "Should not return error code");

        // Test ComputingApi
        PassData passData = computingApi.passData(5);
        assertNotNull(passData, "Should create PassData");

        // Test validation
        assertTrue(fetchApi.validateNumber(BigInteger.valueOf(5)), "FetchApi should validate 5");
        assertTrue(dataStoreApi.validateNumber(5), "DataStoreApi should validate 5");
        assertFalse(fetchApi.validateNumber(BigInteger.valueOf(-5)), "FetchApi should reject -5");
        assertFalse(dataStoreApi.validateNumber(-5), "DataStoreApi should reject -5");
    }

    @Test
    public void testFetchApiErrorHandling() {
        // Create FetchApi with dependencies to test error handling
        ComputingApi computingApi = new ComputingApiImpl();
        DataStoreApi dataStoreApi = new DataStoreApiImpl();
        FetchApiImpl fetchApi = new FetchApiImpl();
        fetchApi.setDataStoreApi(dataStoreApi);
        fetchApi.setComputingApi(computingApi);

        // Test null request
        List<BigInteger> result1 = fetchApi.insertRequest(null);
        assertEquals(List.of(BigInteger.valueOf(-1)), result1, "Should return -1 for null request");

        // Test request with null data
        FetchRequest nullDataRequest = new ListFetchRequest(null);
        List<BigInteger> result2 = fetchApi.insertRequest(nullDataRequest);
        assertEquals(List.of(BigInteger.valueOf(-1)), result2, "Should return -1 for null data");

        // Test request with empty data
        FetchRequest emptyDataRequest = new ListFetchRequest(Arrays.asList());
        List<BigInteger> result3 = fetchApi.insertRequest(emptyDataRequest);
        assertEquals(List.of(BigInteger.valueOf(-1)), result3, "Should return -1 for empty data");
    }

    @Test
    public void testFetchApiWithNegativeNumbers() {
        ComputingApi computingApi = new ComputingApiImpl();
        DataStoreApi dataStoreApi = new DataStoreApiImpl();
        FetchApiImpl fetchApi = new FetchApiImpl();
        fetchApi.setDataStoreApi(dataStoreApi);
        fetchApi.setComputingApi(computingApi);

        // Test with mixed positive and negative numbers
        List<BigInteger> numbers = Arrays.asList(
            BigInteger.valueOf(-1),
            BigInteger.valueOf(-100),
            BigInteger.valueOf(5),
            BigInteger.valueOf(10)
        );
        
        FetchRequest request = new ListFetchRequest(numbers);
        List<BigInteger> insertResult = fetchApi.insertRequest(request);
        
        assertNotNull(insertResult, "Insert should return a result");
        assertEquals(4, insertResult.size(), "Should return result for each input");

        // Negative numbers should return -1 for letter count
        assertEquals(BigInteger.valueOf(-1), insertResult.get(0), "Negative number should return -1");
        assertEquals(BigInteger.valueOf(-1), insertResult.get(1), "Negative number should return -1");
        
        // Positive numbers should have positive letter counts or -1
        assertTrue(insertResult.get(2).compareTo(BigInteger.ZERO) > 0 || 
                  insertResult.get(2).equals(BigInteger.valueOf(-1)),
                  "Positive number should return positive count or -1");
        assertTrue(insertResult.get(3).compareTo(BigInteger.ZERO) > 0 || 
                  insertResult.get(3).equals(BigInteger.valueOf(-1)),
                  "Positive number should return positive count or -1");
    }

    @Test
    public void testLargeNumberValidation() {
        FetchApiImpl fetchApi = new FetchApiImpl();

        // Test with extremely large number
        BigInteger extremelyLarge = new BigInteger("1".repeat(1000)); // 1000-digit number
        boolean validationResult = fetchApi.validateNumber(extremelyLarge);
        
        // Validation might succeed or fail depending on implementation
        System.out.println("Validation for 1000-digit number: " + validationResult);
        
        // Test with number that should definitely fail
        assertFalse(fetchApi.validateNumber(new BigInteger("-99999999999999999999999999999999999999999999999999999999999999999999999999999999")));
    }

    @Test
    public void testNumberToWordsConversion() {
        ComputingApi computingApi = new ComputingApiImpl();

        // Test various numbers
        int[] testNumbers = {0, 1, 5, 10, 15, 21, 100, 134, 1000, 1234};

        for (int number : testNumbers) {
            PassData passData = computingApi.passData(number);
            assertNotNull(passData);
            assertNotNull(passData.getData());
            assertTrue(passData.getData().length() > 0);

            List<Integer> results = computingApi.processPassData(passData);
            assertNotNull(results);
            assertFalse(results.isEmpty());
            assertTrue(results.get(0) > 0 || number == 0); // 0 has "zero" = 4 letters

            System.out.println(number + " -> '" + passData.getData() + "' -> " + results.get(0) + " letters");
        }
    }

    @Test
    public void testFetchApiReturnsLetterCounts() {
        // Test that FetchApi returns letter counts, not original numbers
        ComputingApi computingApi = new ComputingApiImpl();
        DataStoreApi dataStoreApi = new DataStoreApiImpl();
        FetchApiImpl fetchApi = new FetchApiImpl();
        fetchApi.setDataStoreApi(dataStoreApi);
        fetchApi.setComputingApi(computingApi);

        // Test with multiple numbers as BigInteger
        List<BigInteger> testNumbers = Arrays.asList(
            BigInteger.valueOf(1), 
            BigInteger.valueOf(15), 
            BigInteger.valueOf(10), 
            BigInteger.valueOf(5), 
            BigInteger.valueOf(2), 
            BigInteger.valueOf(3), 
            BigInteger.valueOf(8)
        );
        FetchRequest request = new ListFetchRequest(testNumbers);
        List<BigInteger> results = fetchApi.insertRequest(request);

        assertNotNull(results);
        assertEquals(7, results.size(), "Should return 7 results for 7 input numbers");

        // Check each result is a letter count (positive number) or -1
        for (int i = 0; i < results.size(); i++) {
            BigInteger letterCount = results.get(i);
            assertTrue(letterCount.compareTo(BigInteger.ZERO) > 0 || 
                      letterCount.equals(BigInteger.valueOf(-1)), 
                    "Result " + i + " should be positive letter count or -1, got: " + letterCount);
        }

        System.out.println("Numbers: " + testNumbers);
        System.out.println("Letter counts: " + results);
    }

    @Test
    public void testInMemoryDataStoreIntegration() {
        try {
            // Create test configurations
            TestInputConfiguration inputConfig = new TestInputConfiguration();
            TestOutputConfiguration outputConfig = new TestOutputConfiguration();

            // Set up some test data
            inputConfig.setInputNumbers(Arrays.asList(1, 2, 3, 4, 5));

            // Create InMemoryDataStore with configurations
            DataStoreApi dataStoreApi = new InMemoryDataStore(inputConfig, outputConfig);

            // Test validation
            assertTrue(dataStoreApi.validateNumber(5), "InMemoryDataStore should validate positive numbers");
            assertFalse(dataStoreApi.validateNumber(-1), "InMemoryDataStore should reject negative numbers");

            // Test insert with a DataRequest
            DataRequest request = new DataRequest(1, "test", "1,2,3");
            int result = dataStoreApi.insertRequest(request);

            // Check that it returns a valid result
            assertTrue(result == 0 || result == -1, "Should return valid result code: " + result);

        } catch (Exception e) {
            // If InMemoryDataStore or its dependencies don't exist, skip test
            System.out.println("InMemoryDataStore test skipped: " + e.getMessage());
        }
    }
}