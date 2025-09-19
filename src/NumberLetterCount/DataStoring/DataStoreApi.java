package NumberLetterCount.DataStoring;

import NumberLetterCount.Computing.RecieveInfo;
import NumberLetterCount.Computing.SendInfo;
import project.annotations.ProcessAPI;

@ProcessAPI
public interface DataStoreApi {
	
	
	

	 String insertRequest(DataRequest dataRequest);	
	
	 Serialize  serializingData();
	 
	 FormatData formatData();
	 
	 ResultOfCountLetter result();
	 
	 SendInfo sentData();
	 
	 RecieveInfo recieveData();
	 
	 
	

}
