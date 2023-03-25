package examples;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import java.util.List;

public class Person {
    PersonPOJO personPOJO;
    private final String baseUri = "https://localhost:7070";
    private final String dummyEndpointPath = "/auth/cookie/login/";

    public Person(String name, int age, String gender, PersonPOJO.Address address, List<PersonPOJO.PhoneNumber> phoneNumbers){
        RestAssured.baseURI = baseUri;
        personPOJO = new PersonPOJO(name,age,gender,address,phoneNumbers);
    }

    public void convertPOJOToJson(){
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(personPOJO)
                .log().body()
                .post(dummyEndpointPath);
    }
}
