package UserManagementTest;

import UserManagement.UserRegistration;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class UserRegistrationTest {
    @Test
    public void CreatingRequestTest(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateTime = dateFormat.format(new Date());
        Random random = new Random();
        int fourDigitRandom = 1000 + random.nextInt(9000);
        String userName = "TestUser"+dateTime+fourDigitRandom;
        String password = "1";
        UserRegistration userRegistration = new UserRegistration(userName,password);
        userRegistration.createUser();
        Assert.assertEquals(userRegistration.getStatusCode(),201);
        Assert.assertEquals(userRegistration.getResponseBodyUsingKey("username"),userName);
    }
}
