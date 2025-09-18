package NumberLetterCount.Computing;

import NumberLetterCount.DataStoring.RecieveInfo;
import NumberLetterCount.DataStoring.SendInfo;
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
