package UserManagement;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class UserRegistration {

    private final String baseUri = "https://test-api.k6.io";
    private final String userRegisterPath = "/user/register/";

    private String userRegistrationRequestBody;
    private Response response;

    public UserRegistration(String userName, String password){
        RestAssured.baseURI = baseUri;
        userRegistrationRequestBody = "{\"username\": \""+userName+"\",\"password\": \""+password+"\"}";
    }

    public void createUser(){
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(userRegistrationRequestBody)
                .post(userRegisterPath);
    }

    public int getStatusCode(){
        return response.getStatusCode();
    }

    public String getResponseBodyUsingKey(String key){
        JsonPath jsonPath = response.jsonPath();
        return jsonPath.getString(key);
    }

}
