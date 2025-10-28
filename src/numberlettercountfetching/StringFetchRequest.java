package numberlettercountfetching;

import java.util.Arrays;

public class StringFetchRequest extends FetchRequest {
    private String text;

    public StringFetchRequest(String text) {
        super();
        this.text = text;
    }
    
    public String getStringData() {
        return (String) text;
    }
    
//    public void FetchRequest(String text) {
//    	this.text = text;
//    }
}