package app.e2e.crocodiles.flows;

import core.api.APIResponseDetailsExtractor;
import app.e2e.crocodiles.pojo.CrocodilesPOJO;
import io.restassured.RestAssured;
import io.restassured.config.RedirectConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CrocodilesFlow {
    private final String baseUri = "https://test-api.k6.io";
    private final String crocodilesBasePath = "/my/crocodiles/";

    private CrocodilesPOJO crocodilesPOJO;

    public CrocodilesPOJO getCrocodilesPojoResponse() {
        return crocodilesPojoResponse;
    }

    private CrocodilesPOJO crocodilesPojoResponse;

    public CrocodilesFlow(String crocodileName, String gender, String crocodileDOB){
        crocodilesPOJO = new CrocodilesPOJO(crocodileName,gender,crocodileDOB);
        RestAssured.baseURI = baseUri;
    }

    public APIResponseDetailsExtractor createCrocodile(String authToken){
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization","Bearer "+authToken)
                .body(crocodilesPOJO)
                .log().body()
                .post(crocodilesBasePath);

        response.then().log().body();

        crocodilesPojoResponse = response.as(CrocodilesPOJO.class);

        return new APIResponseDetailsExtractor(response);
    }
    public APIResponseDetailsExtractor replaceCrocodile(String authToken, int crocodileId, String crocodileName,String crocodileGender, String crocodileDOB) {
        CrocodilesPOJO replaceCrocodilePOJO = new CrocodilesPOJO(crocodileName,crocodileGender,crocodileDOB);
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization","Bearer "+authToken)
                .body(replaceCrocodilePOJO)
                .log().all()
                .put(crocodilesBasePath+crocodileId);

        response.then().log().all();

        String redirectUrl = response.getHeader("Location");
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization","Bearer "+authToken)
                .body(replaceCrocodilePOJO)
                .log().all()
                .put(baseUri+redirectUrl);

        crocodilesPojoResponse = response.as(CrocodilesPOJO.class);

        response.then().log().all();

        return new APIResponseDetailsExtractor(response);
    }
    public APIResponseDetailsExtractor updateCrocodile(String authToken,int crocodileId, String crocodileName){
        String newName = "{\"name\": \""+crocodileName+"\"}";
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization","Bearer "+authToken)
                .body(newName)
                .log().body()
                .patch(crocodilesBasePath+crocodileId);

        response.then().log().body();

        String redirectUrl = response.getHeader("Location");
        System.out.println("Location "+ redirectUrl);
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization","Bearer "+authToken)
                .body(newName)
                .log().all()
                .patch(baseUri+redirectUrl);

        crocodilesPojoResponse = response.as(CrocodilesPOJO.class);

        return new APIResponseDetailsExtractor(response);
    }
}
