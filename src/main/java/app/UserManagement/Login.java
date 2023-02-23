package app.UserManagement;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Login {
    private final String baseUri = "https://test-api.k6.io";
    private final String loginPath = "/auth/basic/login/";

    private String loginRequestBody;
    private Response response;

    public Login(String userName, String password){
        RestAssured.baseURI = baseUri;
        loginRequestBody = "{\"username\": \""+userName+"\",\"password\": \""+password+"\"}";
    }
    public void performLogin(){
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(loginRequestBody)
                .log().body()
                .post(loginPath);

        response.then().log().body();
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
