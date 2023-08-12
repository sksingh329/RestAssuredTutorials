package examplesTest;

import examples.LogDetails;
import examples.Users;
import examples.UsersPOJO;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class UsersLoggingTest {
    LogDetails users;
    @BeforeMethod
    public void setup(){
        users = new LogDetails();
    }
    @Test
    public void convertResponseBodyToPOJOTest(){
        UsersPOJO usersPOJO = users.convertResponseBodyToPOJO();
        Assert.assertEquals(usersPOJO.getTotalPages(),10);
    }
    @Test
    public void validateUsersDetail(){
        Response response = users.usersDetail();
        assertThat(response.statusCode(),equalTo(200));
        assertThat(response.getHeader("Content-Type"),containsString("application/json"));
        assertThat(response.getHeader("Content-Encoding"),equalTo("gzip"));

        // Validate response body
    }
}
