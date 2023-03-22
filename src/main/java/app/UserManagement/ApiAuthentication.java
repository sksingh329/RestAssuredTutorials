package app.UserManagement;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ApiAuthentication {
    private final String baseUri = "https://test-api.k6.io";
    private final String authCookieEndpointPath = "/auth/cookie/login/";
    private final String authTokenEndpointPath = "/auth/token/login/";

    private String loginRequestBody;
    private Response response;

    public ApiAuthentication(String userName, String password){
        RestAssured.baseURI = baseUri;
        loginRequestBody = "{\"username\": \""+userName+"\",\"password\": \""+password+"\"}";
    }

    public void performLoginUsingCookieAuth(){
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(loginRequestBody)
                .log().body()
                .post(authCookieEndpointPath);

        response.then().log().body();
    }

    public void performLoginUsingTokenAuth(){
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(loginRequestBody)
                .log().body()
                .post(authTokenEndpointPath);

        response.then().log().body();
    }

    public String getCookieValue(String cookieKey){
        return response.getCookie(cookieKey);
    }

    public String getResponseBodyUsingKey(String key){
        JsonPath jsonPath = response.jsonPath();
        return jsonPath.getString(key);
    }
}
