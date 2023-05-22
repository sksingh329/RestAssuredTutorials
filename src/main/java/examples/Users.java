package examples;

import io.restassured.RestAssured;
import io.restassured.response.Response;


public class Users {
    private final String baseUri = "https://reqres.in/";
    private final String usersEndpointPath = "api/users";

    public Users(){
        RestAssured.baseURI = baseUri;
    }

    public UsersPOJO convertResponseBodyToPOJO(){
        Response response = RestAssured.given().get(usersEndpointPath);
        response.then().log().body();
        UsersPOJO usersPOJO = response.as(UsersPOJO.class);
        return usersPOJO;
    }
    public Response usersDetail(){
        Response response = RestAssured.given().get(usersEndpointPath);
        response.then().log().all();
        return response;
    }
}
