package examplesTest;

import examples.Users;
import examples.UsersPOJO;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class UsersPOJOTest {
    Users users;
    @BeforeMethod
    public void setup(){
        users = new Users();
    }
    @Test
    public void convertResponseBodyToPOJOTest(){
        UsersPOJO usersPOJO = users.convertResponseBodyToPOJO();
        Assert.assertEquals(usersPOJO.getTotalPages(),2);
    }
}
