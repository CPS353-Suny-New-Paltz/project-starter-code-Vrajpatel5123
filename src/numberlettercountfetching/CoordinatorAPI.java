package numberlettercountfetching;

import java.util.List;

public interface CoordinatorAPI {
	boolean processFile(String inputFile, String outputFile);
	List<Integer> getLastResults();
}
