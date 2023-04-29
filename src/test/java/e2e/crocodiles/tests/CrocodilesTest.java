package e2e.crocodiles.tests;

import core.api.APIResponseDetailsExtractor;
import app.e2e.crocodiles.flows.CreateCrocodilesFlow;
import app.e2e.crocodiles.flows.CreateUserFlow;
import app.e2e.crocodiles.flows.LoginUserFlow;
import app.e2e.crocodiles.pojo.CrocodilesPOJO;
import core.utils.DateFormatUtils;
import core.utils.RandomNumberUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class CrocodilesTest {
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
    public void createCrocodilesTest(){
        String crocodileName = "Test Crocodile";
        String gender = "M";
        String dob = "2010-06-27";
        CreateCrocodilesFlow createCrocodile = new CreateCrocodilesFlow(crocodileName,gender,dob);
        APIResponseDetailsExtractor createCrocodileResponse = createCrocodile.createCrocodile(authToken);
        //Validate status code is 201
        Assert.assertEquals(createCrocodileResponse.getStatusCode(),201);
        //Validate response body
        CrocodilesPOJO crocodileDetails = createCrocodile.getCrocodilesPojoResponse();
        Assert.assertEquals(crocodileDetails.getCrocodileName(),crocodileName);
        Assert.assertEquals(crocodileDetails.getCrocodileDOB(),dob);
        Assert.assertEquals(crocodileDetails.getGender(),gender);
        assert crocodileDetails.getId() > 0;
        assert crocodileDetails.getAge() > 0;
    }
}
