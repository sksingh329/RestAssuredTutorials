package app.UserManagement;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class MyCrocodiles {
    private final String baseUri = "https://test-api.k6.io";
    private final String myCrocodilesPath = "/my/crocodiles/";

    private Response response;

    public MyCrocodiles(){
        RestAssured.baseURI = baseUri;
    }

    public void getMyCrocodilesWithoutAuth(){
        response = RestAssured.given().get(myCrocodilesPath);

        response.then().log().body();
    }

    public void getMyCrocodilesUsingCookieAuth(String cookieValue){
        response = RestAssured.given().cookie("sessionid",cookieValue).get(myCrocodilesPath);

        response.then().log().body();
    }

    public void getMyCrocodilesUsingTokenAuth(String tokenValue){
        response = RestAssured.given().header("Authorization","Bearer "+tokenValue).get(myCrocodilesPath);

        response.then().log().body();
    }

    public int getStatusCode(){
        return response.getStatusCode();
    }

    public String getResponseBodyUsingKey(String key){
        JsonPath jsonPath = response.jsonPath();
        return jsonPath.getString(key);
    }
}
