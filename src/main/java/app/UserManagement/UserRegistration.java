package app.UserManagement;

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
    public UserRegistration(String userName, String password,String firstName, String lastName,String email){
        RestAssured.baseURI = baseUri;
        userRegistrationRequestBody = "{\"username\": \""+userName+"\",\"password\": \""+password+"\",\"first_name\":\""+firstName+"\",\"last_name\":\""+lastName+"\",\"email\":\""+email+"\"}";
    }

    public void createUser(){
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(userRegistrationRequestBody)
                .log().body()
                .post(userRegisterPath);

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
