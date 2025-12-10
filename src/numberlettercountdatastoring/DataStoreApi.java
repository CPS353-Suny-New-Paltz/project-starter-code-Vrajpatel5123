package numberlettercountdatastoring;
import java.util.List;

import project.annotations.ProcessAPI;

@ProcessAPI
public interface DataStoreApi {
	int insertRequest(DataRequest dataRequest);    
	boolean validateNumber(int number);
	boolean writeResultsToFile(String filePath, List<String> results);
	boolean processFile(String input, String output);
}
