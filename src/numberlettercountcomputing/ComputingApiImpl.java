package numberlettercountcomputing;
import java.util.List;

import numberlettercountdatastoring.DataStoreApi;
import numberlettercountfetching.FetchApi;

public class ComputingApiImpl implements ComputingApi {
    private FetchApi fetchApi;
    private DataStoreApi dataStoreApi;
    
    @Override
    public int initalize(List<Integer> inputData) {
        return -1; // failure value
    }
    
    @Override
    public List<Integer> compute() {
        return null;
    }
    
    @Override
    public String writeResult(String result, String delimiters) {
        return ""; // empty string
    }
    
    @Override
    public void insertRequest() {
        // empty implementation
    }
    
    @Override
    public Extract extractData() {
        return null;
    }
    
    @Override
    public SendInfo sendInfo() {
        return null;
    }
    
    @Override
    public RecieveInfo recieveInfo() {
        return null;
    }
    
    @Override
    public ProcessData processData() {
        return null;
    }
    
    @Override
    public PassData passData() {
        return null;
    }
}