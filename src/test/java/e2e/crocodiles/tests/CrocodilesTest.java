package e2e.crocodiles.tests;

import core.api.APIResponseDetailsExtractor;
import app.e2e.crocodiles.flows.CrocodilesFlow;
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
    private CrocodilesFlow createCrocodileFlow;

    public APIResponseDetailsExtractor createCrocodile(String crocodileName,String gender, String dob){
        createCrocodileFlow = new CrocodilesFlow(crocodileName,gender,dob);
        APIResponseDetailsExtractor createCrocodileResponse = createCrocodileFlow.createCrocodile(authToken);
        return createCrocodileResponse;
    }
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
        APIResponseDetailsExtractor createCrocodileResponse = createCrocodile(crocodileName,gender,dob);
        //Validate status code is 201
        Assert.assertEquals(createCrocodileResponse.getStatusCode(),201);
        //Validate response body
        CrocodilesPOJO crocodileDetails = createCrocodileFlow.getCrocodilesPojoResponse();
        Assert.assertEquals(crocodileDetails.getCrocodileName(),crocodileName);
        Assert.assertEquals(crocodileDetails.getCrocodileDOB(),dob);
        Assert.assertEquals(crocodileDetails.getGender(),gender);
        assert crocodileDetails.getId() > 0;
        assert crocodileDetails.getAge() > 0;
    }
    @Test
    public void replaceCrocodilesTest(){
        String crocodileName = "Test Crocodile";
        String gender = "M";
        String dob = "2010-06-27";
        String crocodileNewName = "Replaced Crocodile";

        APIResponseDetailsExtractor createCrocodileResponse = createCrocodile(crocodileName,gender,dob);
        //Validate status code is 201
        Assert.assertEquals(createCrocodileResponse.getStatusCode(),201);
        //Get crocodile id
        CrocodilesPOJO crocodileDetails = createCrocodileFlow.getCrocodilesPojoResponse();
        int crocodileId = crocodileDetails.getId();
        System.out.println("Crocodile id is: "+crocodileId);

        APIResponseDetailsExtractor replacedCrocodileResponse = createCrocodileFlow.replaceCrocodile(authToken,crocodileId,crocodileNewName,gender,dob);
        Assert.assertEquals(replacedCrocodileResponse.getStatusCode(),200);

        //Validate crocodile name is updated
        CrocodilesPOJO replacedCrocodileDetails = createCrocodileFlow.getCrocodilesPojoResponse();
        Assert.assertEquals(replacedCrocodileDetails.getCrocodileName(),crocodileNewName);
    }
    @Test
    public void updateCrocodilesTest(){
        String crocodileName = "Test Crocodile";
        String gender = "M";
        String dob = "2010-06-27";
        String crocodileNewName = "Updated Crocodile";

        APIResponseDetailsExtractor createCrocodileResponse = createCrocodile(crocodileName,gender,dob);
        //Validate status code is 201
        Assert.assertEquals(createCrocodileResponse.getStatusCode(),201);
        //Get crocodile id
        CrocodilesPOJO crocodileDetails = createCrocodileFlow.getCrocodilesPojoResponse();
        int crocodileId = crocodileDetails.getId();
        System.out.println("Crocodile id is: "+crocodileId);

        APIResponseDetailsExtractor updatedCrocodileResponse = createCrocodileFlow.updateCrocodile(authToken,crocodileId,crocodileNewName);
        Assert.assertEquals(updatedCrocodileResponse.getStatusCode(),200);

        //Validate crocodile name is updated
        CrocodilesPOJO updatedCrocodileDetails = createCrocodileFlow.getCrocodilesPojoResponse();
        Assert.assertEquals(updatedCrocodileDetails.getCrocodileName(),crocodileNewName);
    }
}
