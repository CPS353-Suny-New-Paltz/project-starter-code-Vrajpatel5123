package numberlettercountfetching;
import java.util.List;

public class SingleThreadedNetworkAPI implements FetchApi {
	private final FetchApi delegate;

	public SingleThreadedNetworkAPI() {
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
		return delegate.insertRequest(fetchRequest);
	}


	public boolean validateNumber(int number) {
		return delegate.validateNumber(number);
	}
}
