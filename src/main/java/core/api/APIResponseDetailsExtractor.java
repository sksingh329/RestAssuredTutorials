package core.api;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class APIResponseDetailsExtractor {
    private Response response;

    public APIResponseDetailsExtractor(Response response){
        this.response = response;
    }
    public int getStatusCode(){
        return response.getStatusCode();
    }

    public String getResponseBodyUsingKey(String key){
        JsonPath jsonPath = response.jsonPath();
        return jsonPath.getString(key);
    }

    public String getResponseHeaderUsingKey(String key){
        return response.getHeader(key);
    }
}
