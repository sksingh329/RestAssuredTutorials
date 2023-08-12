package examplesTest;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;

public class CrocodilesSchemaTest {
    @Test
    public void crocodilesSchemaTest(){
        RestAssured.baseURI = "https://test-api.k6.io";
        RestAssured.given()
                .when()
                .get("/public/crocodiles/")
                .then().log().all()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("crocodiles_schema.json"));
    }
}
