package UserManagement;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class UserRegistration {
    private Response response;
    private String baseUri = "https://test-api.k6.io";
    private String userRegister = "/user/register/";

    private String userRegistrationRequestBody;
    public UserRegistration(String userName, String password){
        RestAssured.baseURI = baseUri;
        userRegistrationRequestBody = "{\"username\": \""+userName+"\",\"password\": \""+password+"\"}";
    }

    public void createUser(){
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(userRegistrationRequestBody)
                .post(userRegister);
    }

    public int getStatusCode(){
        return response.getStatusCode();
    }

    public String getResponseBodyUsingKey(String key){
        JsonPath jsonPath = response.jsonPath();
        return jsonPath.getString(key);
    }

}
