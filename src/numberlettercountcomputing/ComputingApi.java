package numberlettercountcomputing;

import java.math.BigInteger;
import java.util.List;

import project.annotations.ConceptualAPI;


@ConceptualAPI
public interface ComputingApi {
	PassData passData(int number);
	List<Integer> processPassData(PassData passData); 
	BigInteger processLargeNumber(BigInteger number);
}