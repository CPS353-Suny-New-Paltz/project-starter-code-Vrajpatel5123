package numberlettercountfetching;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.List;


public class MultithreadedNetworkAPI implements FetchApi {
	private static final int MAX_THREADS = 4; // Reasonable upper bound
	private final ExecutorService executor;
	private final FetchApi delegate;

	public MultithreadedNetworkAPI() {
		this.executor = Executors.newFixedThreadPool(MAX_THREADS);
		this.delegate = createDelegate();
	}

	private FetchApi createDelegate() {
		// Create the single-threaded implementation
		numberlettercountcomputing.ComputingApi computingApi = new numberlettercountcomputing.ComputingApiImpl();
		numberlettercountdatastoring.DataStoreApi dataStoreApi = new numberlettercountdatastoring.DataStoreApiImpl(computingApi);
		FetchApiImpl fetchApi = new FetchApiImpl();
		fetchApi.setDataStoreApi(dataStoreApi);
		return fetchApi;
	}


	public List<Integer> insertRequest(FetchRequest fetchRequest) {
		try {
			Future<List<Integer>> future = executor.submit(() -> 
			delegate.insertRequest(fetchRequest)
					);
			return future.get();
		} catch (Exception e) {
			System.err.println("Multithreaded processing error: " + e.getMessage());
			return List.of(-1); // Error indicator
		}
	}


	public boolean validateNumber(int number) {
		try {
			Future<Boolean> future = executor.submit(() -> 
			delegate.validateNumber(number)
					);
			return future.get();
		} catch (Exception e) {
			System.err.println("Multithreaded validation error: " + e.getMessage());
			return false;
		}
	}

	public void shutdown() {
		executor.shutdown();
	}
}
