package project.checkpointtests;

import numberlettercountfetching.FetchApiImpl;
import numberlettercountcomputing.ComputingApi;
import numberlettercountcomputing.ComputingApiImpl;
import numberlettercountdatastoring.DataStoreApi;
import numberlettercountdatastoring.DataStoreApiImpl;

public class ManualTestingFramework {

	public static final String INPUT = "manualTestInput.txt";
	public static final String OUTPUT = "manualTestOutput.txt";

	public static void main(String[] args) throws Exception {
		System.out.println("=== Starting Manual Testing Framework ===");

		// 1. Create FetchApiImpl
		FetchApiImpl fetchApi = new FetchApiImpl();

		// 2. Create the ComputingApi and DataStoreApi instances
		ComputingApi computingApi = new ComputingApiImpl();
		DataStoreApi dataStoreApi = new DataStoreApiImpl();

		// 3. Set the dependencies on FetchApiImpl
		fetchApi.setComputingApi(computingApi);
		fetchApi.setDataStoreApi(dataStoreApi);

		System.out.println("✓ Created FetchApiImpl with all dependencies set");
		System.out.println("✓ APIs are now ready to coordinate");

		System.out.println("\n=== Manual Testing Framework Finished ===");
	}
}