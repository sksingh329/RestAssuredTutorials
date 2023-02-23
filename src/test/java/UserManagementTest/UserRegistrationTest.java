package UserManagementTest;

import app.UserManagement.Login;
import app.UserManagement.UserRegistration;
import core.utils.DateFormatUtils;
import core.utils.RandomNumberUtils;
import org.apache.commons.lang3.Validate;
import org.testng.Assert;
import org.testng.annotations.Test;


public class UserRegistrationTest {
    @Test
    public void userRegistrationWithMandatoryFieldTest(){
        String dateTime = DateFormatUtils.getTimeStamp("yyyyMMddHHmmss");
        int fourDigitRandom = RandomNumberUtils.getRandomNumber(4);
        String userName = "TestUser"+dateTime+fourDigitRandom;
        String password = "1";
        UserRegistration userRegistration = new UserRegistration(userName,password);
        userRegistration.createUser();
        Assert.assertEquals(userRegistration.getStatusCode(),201);
        Assert.assertEquals(userRegistration.getResponseBodyUsingKey("username"),userName);
    }
    @Test
    public void userRegistrationWithAllFieldsTest(){
        String dateTime = DateFormatUtils.getTimeStamp("yyyyMMddHHmmss");
        int fourDigitRandom = RandomNumberUtils.getRandomNumber(4);

        String password = "1";
        String firstName = "TestUser";
        String lastName =  dateTime+fourDigitRandom;
        String userName = firstName+lastName;
        String email = userName+"@tester.com";

        UserRegistration userRegistration = new UserRegistration(userName,password,firstName,lastName,email);
        userRegistration.createUser();

        Assert.assertEquals(userRegistration.getStatusCode(),201);
        // Validate response header
        Assert.assertEquals(userRegistration.getResponseHeaderUsingKey("Content-Type"),"application/json");
        assert Integer.parseInt(userRegistration.getResponseHeaderUsingKey("Content-Length")) > 0;
        //Validate response body
        Assert.assertEquals(userRegistration.getResponseBodyUsingKey("username"),userName);
        Assert.assertEquals(userRegistration.getResponseBodyUsingKey("first_name"),firstName);
        Assert.assertEquals(userRegistration.getResponseBodyUsingKey("last_name"),lastName);
        Assert.assertEquals(userRegistration.getResponseBodyUsingKey("email"),email);
    }
    @Test
    public void validateLoginForNewAccountTest() throws InterruptedException {
        String dateTime = DateFormatUtils.getTimeStamp("yyyyMMddHHmmss");
        int fourDigitRandom = RandomNumberUtils.getRandomNumber(4);

        String password = "1";
        String firstName = "TestUser";
        String lastName = dateTime + fourDigitRandom;
        String userName = firstName + lastName;
        String email = userName + "@tester.com";

        UserRegistration userRegistration = new UserRegistration(userName, password, firstName, lastName, email);
        userRegistration.createUser();

        Login login = new Login(userName,password);
        login.performLogin();

        Assert.assertEquals(login.getStatusCode(),200);

        Assert.assertEquals(login.getResponseBodyUsingKey("username"),userName);
        Assert.assertEquals(login.getResponseBodyUsingKey("first_name"),firstName);
        Assert.assertEquals(login.getResponseBodyUsingKey("last_name"),lastName);
        Assert.assertEquals(login.getResponseBodyUsingKey("email"),email);
    }
    @Test
    public void validateDuplicateUserNameIsNotAllowedForUserRegistration() {
        String dateTime = DateFormatUtils.getTimeStamp("yyyyMMddHHmmss");
        int fourDigitRandom = RandomNumberUtils.getRandomNumber(4);

        String password = "1";
        String firstName = "TestUser";
        String lastName = dateTime + fourDigitRandom;
        String userName = firstName + lastName;
        String email = userName + "@tester.com";

        String duplicateUserMessage = "[A user with that username already exists.]";
        String duplicateEmailMessage = "[User with this email already exists!]";

        UserRegistration userRegistration = new UserRegistration(userName, password, firstName, lastName, email);
        userRegistration.createUser();

        UserRegistration duplicateUserRegistration = new UserRegistration(userName, password, firstName, lastName, email);
        duplicateUserRegistration.createUser();

        Assert.assertEquals(duplicateUserRegistration.getStatusCode(),400);

        Assert.assertEquals(duplicateUserRegistration.getResponseBodyUsingKey("username"),duplicateUserMessage);
        Assert.assertEquals(duplicateUserRegistration.getResponseBodyUsingKey("email"),duplicateEmailMessage);
    }
}
