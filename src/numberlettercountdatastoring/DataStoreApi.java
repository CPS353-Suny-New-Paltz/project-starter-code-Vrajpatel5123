package numberlettercountdatastoring;
import java.util.List;

import project.annotations.ProcessAPI;

@ProcessAPI
public interface DataStoreApi {
	int insertRequest(DataRequest dataRequest);    
	boolean validateNumber(int number);
	boolean writeResultsToFile(String filePath, List<String> results);
	// Read numbers from an input file and return the list of integers found
	List<Integer> processFile(String input);
}
