package app.e2e.crocodiles.flows;

import core.api.APIResponseDetailsExtractor;
import app.e2e.crocodiles.pojo.CrocodilesPOJO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CreateCrocodilesFlow {
    private final String baseUri = "https://test-api.k6.io";
    private final String createCrocodilesPath = "/my/crocodiles/";

    private CrocodilesPOJO crocodilesPOJO;

    public CrocodilesPOJO getCrocodilesPojoResponse() {
        return crocodilesPojoResponse;
    }

    private CrocodilesPOJO crocodilesPojoResponse;

    public CreateCrocodilesFlow(String crocodileName, String gender, String crocodileDOB){
        crocodilesPOJO = new CrocodilesPOJO(crocodileName,gender,crocodileDOB);
        RestAssured.baseURI = baseUri;
    }

    public APIResponseDetailsExtractor createCrocodile(String authToken){
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization","Bearer "+authToken)
                .body(crocodilesPOJO)
                .log().body()
                .post(createCrocodilesPath);

        response.then().log().body();

        crocodilesPojoResponse = response.as(CrocodilesPOJO.class);

        return new APIResponseDetailsExtractor(response);
    }
}
