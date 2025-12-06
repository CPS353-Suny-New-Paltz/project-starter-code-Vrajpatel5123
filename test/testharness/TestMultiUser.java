package testharness;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import numberlettercountfetching.MultithreadedNetworkAPI;

public class TestMultiUser {

	// TODO 1: Changed to your @NetworkAPI interface type
	private numberlettercountfetching.FetchApi coordinator;
	private MultithreadedNetworkAPI networkAPI;

	@BeforeEach
	public void initializeComputeEngine() {
		// TODO 2: Create instance of your @NetworkAPI implementation
		networkAPI = new MultithreadedNetworkAPI();
		coordinator = networkAPI; // MultithreadedNetworkAPI implements FetchApi
	}

	@AfterEach
	public void cleanup() {
		if (networkAPI != null) {
			networkAPI.shutdown();
		}
	}

	@Test
	public void compareMultiAndSingleThreaded() throws Exception {
		int nThreads = 100;
		List<TestUser> testUsers = new ArrayList<>();
		for (int i = 0; i < nThreads; i++) {
			testUsers.add(new TestUser(coordinator));
		}

		// Run single threaded
		String singleThreadFilePrefix = "testMultiUser.compareMultiAndSingleThreaded.test.singleThreadOut.tmp";
		for (int i = 0; i < nThreads; i++) {
			File singleThreadedOut = new File(singleThreadFilePrefix + i);
			singleThreadedOut.deleteOnExit();
			testUsers.get(i).run(singleThreadedOut.getCanonicalPath());
		}

		// Run multi threaded
		ExecutorService threadPool = Executors.newCachedThreadPool();
		List<Future<?>> results = new ArrayList<>();
		String multiThreadFilePrefix = "testMultiUser.compareMultiAndSingleThreaded.test.multiThreadOut.tmp";
		for (int i = 0; i < nThreads; i++) {
			File multiThreadedOut = new File(multiThreadFilePrefix + i);
			multiThreadedOut.deleteOnExit();
			String multiThreadOutputPath = multiThreadedOut.getCanonicalPath();
			TestUser testUser = testUsers.get(i);
			results.add(threadPool.submit(() -> testUser.run(multiThreadOutputPath)));
		}

		results.forEach(future -> {
			try {
				future.get();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});

		threadPool.shutdown();

		// Check that the output is the same for multi-threaded and single-threaded
		List<String> singleThreaded = loadAllOutput(singleThreadFilePrefix, nThreads);
		List<String> multiThreaded = loadAllOutput(multiThreadFilePrefix, nThreads);
		assertEquals(singleThreaded, multiThreaded);
	}

	@Test
	public void smokeTest() {
		// Basic smoke test to verify the API works
		List<Integer> testNumbers = List.of(1, 2, 3);
		numberlettercountfetching.ListFetchRequest request = 
				new numberlettercountfetching.ListFetchRequest(testNumbers);

		List<Integer> result = coordinator.insertRequest(request);

		assertNotNull(result);
		assertFalse(result.isEmpty());
	}

	private List<String> loadAllOutput(String prefix, int nThreads) throws IOException {
		List<String> result = new ArrayList<>();
		for (int i = 0; i < nThreads; i++) {
			File file = new File(prefix + i);
			if (file.exists()) {
				result.addAll(Files.readAllLines(file.toPath()));
			}
		}
		return result;
	}
}