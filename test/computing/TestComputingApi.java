package computing;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import numberlettercountcomputing.ComputingApiImpl;
import numberlettercountdatastoring.DataStoreApi;
import numberlettercountfetching.FetchApi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

public class TestComputingApi {
    
    @Test
    public void testInitializeAndCompute() {
        // Create mock dependencies
        FetchApi mockFetchApi = Mockito.mock(FetchApi.class);
        DataStoreApi mockDataStoreApi = Mockito.mock(DataStoreApi.class);
        
        // Create implementation with mocked dependencies
        ComputingApiImpl computingApi = new ComputingApiImpl();
        // Note: We'll need to add setter methods for dependencies
        
        // Test will fail because implementation returns failure value
        List<Integer> inputData = Arrays.asList(1, 2, 3);
        String Result = computingApi.initalize(inputData);
        String Result1 = "one,two,three";
        
        assertEquals(Result,Result1);
        
        
        
        // This test will fail as expected
        
    }
}

