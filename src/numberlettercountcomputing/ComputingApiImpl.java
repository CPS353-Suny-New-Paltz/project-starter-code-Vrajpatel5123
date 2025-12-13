package numberlettercountcomputing;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import numberlettercountfetching.FetchApi;
import numberlettercountfetching.FetchRequest;

public class ComputingApiImpl implements ComputingApi {
    private static final Logger logger = Logger.getLogger(ComputingApiImpl.class.getName());
    private FetchApi fetchApi;

    // Arrays for number words
    private static final String[] UNITS = {
        "", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine",
        "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", 
        "sixteen", "seventeen", "eighteen", "nineteen"
    };

    private static final String[] TENS = {
        "", "", "twenty", "thirty", "forty", "fifty", 
        "sixty", "seventy", "eighty", "ninety"
    };

    private static final String[] THOUSANDS = {
        "", "thousand", "million", "billion", "trillion"
    };

    public ComputingApiImpl() {
        logger.info("ComputingApiImpl created");
    }

    // Setter for FetchApi dependency
    public void setFetchApi(FetchApi fetchApi) {
        this.fetchApi = fetchApi;
        logger.info("FetchApi dependency set on ComputingApiImpl");
    }

    public PassData passData(int number) {
        try {
            // Parameter validation
            if (number < 0) {
                logger.warning("Negative number provided: " + number);
                PassData errorData = new PassData();
                errorData.setData("ERROR: Negative number");
                errorData.setFromComponent("error_handler");
                errorData.setToComponent("validation");
                return errorData;
            }

            PassData passData = new PassData();

            // Convert number to words
            String word = convertNumberToWords(number);

            passData.setData(word);
            passData.setFromComponent("number_converter");
            passData.setToComponent("letter_counter");

            logger.info("Created PassData for number " + number + ": " + passData);
            return passData;

        } catch (Exception e) {
            logger.severe("Error in passData: " + e.getMessage());
            PassData errorData = new PassData();
            errorData.setData("ERROR");
            errorData.setFromComponent("error_handler");
            errorData.setToComponent("recovery");
            return errorData;
        }
    }

    public List<Integer> processPassData(PassData passData) {
        try {
            if (passData == null) {
                return Arrays.asList(-1);
            }

            List<Integer> resultList = new ArrayList<>();
            String data = passData.getData();

            if (data != null && !data.isEmpty() && !data.startsWith("ERROR")) {
                // KEY FIX: Remove "and" completely before counting
                // Use regex to remove the word "and" with spaces around it
                String cleaned = data.replaceAll("\\s+and\\s+", " ");
                // Also handle "and" at the end
                cleaned = cleaned.replaceAll("\\s+and$", "");
                // Also handle "and" at the beginning (unlikely but safe)
                cleaned = cleaned.replaceAll("^and\\s+", "");

                // Now count only letters (ignore spaces, hyphens, etc.)
                int letterCount = cleaned.replaceAll("[^a-zA-Z]", "").length();

                resultList.add(letterCount);
                resultList.add(data.length()); // Original length for reference
            } else {
                resultList.add(0);
                resultList.add(0);
            }

            return resultList;

        } catch (Exception e) {
            return Arrays.asList(-1);
        }
    }

    // New method that uses FetchApi to process numbers
    public List<BigInteger> computeNumbers(List<BigInteger> numbers) {
        List<BigInteger> results = new ArrayList<>();
        if (numbers == null) {
            return results;
        }

        for (BigInteger number : numbers) {
            try {
                results.add(processLargeNumber(number));
            } catch (Exception e) {
                logger.warning("Error computing number " + number + ": " + e.getMessage());
                results.add(BigInteger.valueOf(-1));
            }
        }

        return results;
    }

    // Process a single number using FetchApi
    public BigInteger computeNumber(BigInteger number) {
        try {
            return processLargeNumber(number);
        } catch (Exception e) {
            logger.severe("Error in computeNumber: " + e.getMessage());
            return BigInteger.valueOf(-1);
        }
    }

    // In ComputingApiImpl class
    public BigInteger processLargeNumber(BigInteger number) {
        if (number == null) {
            return BigInteger.valueOf(-1);
        }

        try {
            if (number.equals(BigInteger.ZERO)) {
                return BigInteger.valueOf(4); // "zero" = 4 letters
            }

            // Try to process using passData if it fits in int
            if (number.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) <= 0 && 
                number.compareTo(BigInteger.valueOf(0)) >= 0) {

                int intNumber = number.intValue();
                PassData passData = passData(intNumber);
                List<Integer> results = processPassData(passData);

                if (results != null && !results.isEmpty()) {
                    return BigInteger.valueOf(results.get(0));
                }
            }

            logger.warning("Large number processing not fully implemented for: " + number);
            return BigInteger.valueOf(-1);

        } catch (Exception e) {
            logger.severe("Error processing large number: " + e.getMessage());
            return BigInteger.valueOf(-1);
        }
    }

    // Helper method to convert number to words (British English)
    private String convertNumberToWords(int number) {
        if (number == 0) {
            return "zero";
        }

        String words = "";
        int thousandIndex = 0;
        int num = number;

        while (num > 0) {
            if (num % 1000 != 0) {
                words = convertHundreds(num % 1000) + 
                        (thousandIndex > 0 ? " " + THOUSANDS[thousandIndex] : "") + 
                        (words.isEmpty() ? "" : " ") + words;
            }
            num /= 1000;
            thousandIndex++;
        }

        return words.trim();
    }

    // Update just the convertHundreds method in ComputingApiImpl.java:
    private String convertHundreds(int number) {
        String words = "";

        // Handle hundreds
        if (number >= 100) {
            words += UNITS[number / 100] + " hundred";
            number %= 100;
            // According to British English: use "and" for numbers 100-999 when remainder > 0
            if (number > 0) {
                words += " and ";
            }
        }

        // Handle tens and units
        if (number >= 20) {
            words += TENS[number / 10];
            number %= 10;
            if (number > 0) {
                words += "-" + UNITS[number];
            }
        } else if (number > 0) {
            words += UNITS[number];
        }

        return words;
    }
}