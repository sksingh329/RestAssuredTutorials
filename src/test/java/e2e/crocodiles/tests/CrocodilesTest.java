package e2e.crocodiles.tests;

import core.api.APIResponseDetailsExtractor;
import app.e2e.crocodiles.flows.CrocodilesFlow;
import app.e2e.crocodiles.flows.CreateUserFlow;
import app.e2e.crocodiles.flows.LoginUserFlow;
import app.e2e.crocodiles.pojo.CrocodilesPOJO;
import core.utils.DateFormatUtils;
import core.utils.RandomNumberUtils;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;

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
    @Test
    public void validateCrocodilesTest() {

        APIResponseDetailsExtractor createCrocodileResponse;

        String[][] crocodileDetails = {{"Test Crocodile1","M","2010-06-27"},{"Test Crocodile2","F","2018-06-27"},{"Test Crocodile3","M","2007-06-27"}};
        List<Integer> crocodileIds = new ArrayList<>();
        for (String[] crocodileDetail : crocodileDetails) {
            createCrocodileResponse = createCrocodile(crocodileDetail[0], crocodileDetail[1], crocodileDetail[2]);
            assertThat(createCrocodileResponse.getStatusCode(), equalTo(201));
            crocodileIds.add(Integer.parseInt(createCrocodileResponse.getResponseBodyUsingKey("id")));
        }

        APIResponseDetailsExtractor getCrocodileResponse = createCrocodileFlow.getCrocodile(authToken);
        //Validate status code
        assertThat(getCrocodileResponse.getStatusCode(),equalTo(200));
        //Validate header
        assertThat(getCrocodileResponse.getResponseHeaderUsingKey("Content-Type"),containsString("application/json"));
        assertThat(Integer.parseInt(getCrocodileResponse.getResponseHeaderUsingKey("Content-Length")),greaterThan(100));
        //Validate response body
        JsonPath jsonPath = getCrocodileResponse.getResponseJsonPath();
        int crocodilesCount = jsonPath.getList("").size();
        assertThat(crocodilesCount,equalTo(crocodileDetails.length));
        List<Integer> crocodileIdsList = jsonPath.getList("id");
        assertThat(crocodileIdsList,hasItems(crocodileIds.get(0),crocodileIds.get(1),crocodileIds.get(2)));

        //Validate response body using condition
        List<Integer> crocodileIdsListHavingAgeGreaterThan10 = jsonPath.getList("findAll { it.age > 10 }.id");
        assertThat(crocodileIdsListHavingAgeGreaterThan10,hasItems(crocodileIds.get(0),crocodileIds.get(2)));

        List<Integer> crocodileIdsListHavingGenderFemale = jsonPath.getList("findAll { it.sex == 'F' }.id");
        assertThat(crocodileIdsListHavingGenderFemale,hasItems(crocodileIds.get(1)));
    }

    @Test
    public void deleteCrocodilesTest() {

        APIResponseDetailsExtractor createCrocodileResponse;

        String[][] crocodileDetails = {{"Test Crocodile1", "M", "2010-06-27"}, {"Test Crocodile2", "F", "2018-06-27"}, {"Test Crocodile3", "M", "2007-06-27"}};
        List<Integer> crocodileIds = new ArrayList<>();
        for (String[] crocodileDetail : crocodileDetails) {
            createCrocodileResponse = createCrocodile(crocodileDetail[0], crocodileDetail[1], crocodileDetail[2]);
            assertThat(createCrocodileResponse.getStatusCode(), equalTo(201));
            crocodileIds.add(Integer.parseInt(createCrocodileResponse.getResponseBodyUsingKey("id")));
        }
        int crocodileIdToDelete = crocodileIds.get(0);
        APIResponseDetailsExtractor deleteCrocodileResponse = createCrocodileFlow.deleteCrocodile(authToken,crocodileIdToDelete);

        //Validate status code of DELETE response
        assertThat(deleteCrocodileResponse.getStatusCode(),equalTo(204));

        APIResponseDetailsExtractor getCrocodileResponse = createCrocodileFlow.getCrocodile(authToken);
        //Validate status code of GET response
        assertThat(getCrocodileResponse.getStatusCode(),equalTo(200));

        //Validate response body
        JsonPath jsonPath = getCrocodileResponse.getResponseJsonPath();
        int crocodilesCount = jsonPath.getList("").size();
        assertThat(crocodilesCount,equalTo(crocodileDetails.length-1));
        List<Integer> crocodileIdsList = jsonPath.getList("id");
        //Validate that deleted crocodile id does not exist
        assertThat(crocodileIdsList,not(hasItems(crocodileIdToDelete)));
        //Validate that other crocodile id exist
        assertThat(crocodileIdsList,hasItems(crocodileIds.get(1),crocodileIds.get(2)));
    }
}
