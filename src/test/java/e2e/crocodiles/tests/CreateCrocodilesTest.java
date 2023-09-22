package e2e.crocodiles.tests;

import app.e2e.crocodiles.flows.CreateUserFlow;
import app.e2e.crocodiles.flows.LoginUserFlow;
import app.e2e.crocodiles.pojo.CrocodilesPOJOUsingLombok;
import core.api.APIResponseDetailsExtractor;
import core.utils.DateFormatUtils;
import core.utils.RandomNumberUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class CreateCrocodilesTest {
    private String authToken;

    @BeforeTest
    public void createUserAndGetToken(){
        String dateTime = DateFormatUtils.getTimeStamp("yyyyMMddHHmmss");
        int fourDigitRandom = RandomNumberUtils.getRandomNumber(4);

        String password = "1";
        String firstName = "TestUser";
        String lastName = dateTime + fourDigitRandom;
        String userName = firstName + lastName;
        String email = userName + "@tester.com";

        CreateUserFlow newUser = new CreateUserFlow(userName,firstName,lastName,email,password);
        APIResponseDetailsExtractor newUserResponse = newUser.createUser();
        Assert.assertEquals(newUserResponse.getStatusCode(),201);

        LoginUserFlow userLogin = new LoginUserFlow(userName,password);
        APIResponseDetailsExtractor userLoginResponse = userLogin.performLoginUsingTokenAuth();
        authToken = userLoginResponse.getResponseBodyUsingKey("access");
    }
    @Test
    public void createCrocodilesUsingPOJOTest(){
        String crocodileName = "Test Crocodile";
        String gender = "M";
        String dob = "2010-06-27";
        int age = 13;

        CrocodilesPOJOUsingLombok crocodilesPOJOUsingLombok = new CrocodilesPOJOUsingLombok(crocodileName,gender,dob);

        RestAssured.baseURI = "https://test-api.k6.io/";

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization","Bearer "+authToken)
                .body(crocodilesPOJOUsingLombok)
                .when()
                .post("/my/crocodiles/");
        response.prettyPrint();

        Assert.assertEquals(response.statusCode(),201);
        CrocodilesPOJOUsingLombok crocodilesResponse = response.as(CrocodilesPOJOUsingLombok.class);
        Assert.assertEquals(crocodilesResponse.getCrocodileName(),crocodileName);
        Assert.assertEquals(crocodilesResponse.getGender(),gender);
        Assert.assertEquals(crocodilesResponse.getCrocodileDOB(),dob);
        Assert.assertEquals(crocodilesResponse.getAge(),age);
    }
}
