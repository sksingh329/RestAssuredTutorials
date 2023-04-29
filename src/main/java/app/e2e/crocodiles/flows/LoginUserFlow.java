package app.e2e.crocodiles.flows;

import core.api.APIResponseDetailsExtractor;
import app.e2e.crocodiles.pojo.LoginUserPOJO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class LoginUserFlow {
    private final String baseUri = "https://test-api.k6.io";
    private final String authTokenEndpointPath = "/auth/token/login/";

    private LoginUserPOJO loginUserPOJO;

    public LoginUserFlow(String userName, String password){
        loginUserPOJO = new LoginUserPOJO(userName,password);
        RestAssured.baseURI = baseUri;
    }
    public APIResponseDetailsExtractor performLoginUsingTokenAuth(){
       Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(loginUserPOJO)
                .log().body()
                .post(authTokenEndpointPath);

        response.then().log().body();

        return new APIResponseDetailsExtractor(response);
    }
}
