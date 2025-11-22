package numberlettercountdatastoring;




import project.annotations.ProcessAPI;

@ProcessAPI
public interface DataStoreApi {




	int insertRequest(DataRequest dataRequest);	

	//	Serialize  serializingData();
	//
	//	FormatData formatData();
	//
	//	ResultOfCountLetter result();
	//
	//	SendInfo sentData();
	//
	//	RecieveInfo recieveData();


	//	List<Integer> fetchAllData();
	//    boolean processRequest();
	boolean validateNumber(int number);



}
