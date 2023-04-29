package app.e2e.crocodiles.flows;

import core.api.APIResponseDetailsExtractor;
import app.e2e.crocodiles.pojo.CreateUserPOJO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CreateUserFlow {
    private final String baseUri = "https://test-api.k6.io";
    private final String userRegisterPath = "/user/register/";

    private CreateUserPOJO createUserPOJO;

    public CreateUserFlow(String userName, String firstName, String lastName, String email, String password){
        createUserPOJO = new CreateUserPOJO(userName,firstName,lastName,email,password);
        RestAssured.baseURI = baseUri;
    }

    public APIResponseDetailsExtractor createUser(){
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(createUserPOJO)
                .log().body()
                .post(userRegisterPath);

        response.then().log().body();

        return new APIResponseDetailsExtractor(response);
    }
}
