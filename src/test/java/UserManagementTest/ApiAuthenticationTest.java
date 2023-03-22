package UserManagementTest;

import app.UserManagement.ApiAuthentication;
import app.UserManagement.MyCrocodiles;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ApiAuthenticationTest {

    private String userName = "TestUser2023020720401211";
    private String password = "1";

    private ApiAuthentication apiAuthentication;
    private MyCrocodiles myCrocodiles;

    @BeforeMethod
    public void setup(){
        apiAuthentication = new ApiAuthentication(userName,password);
        myCrocodiles = new MyCrocodiles();
    }

    @Test
    public void validateAuthErrorMyCrocodiles(){
        myCrocodiles.getMyCrocodilesWithoutAuth();
        Assert.assertEquals(myCrocodiles.getStatusCode(),401);
        Assert.assertEquals(myCrocodiles.getResponseBodyUsingKey("detail"),"Authentication credentials were not provided.");
    }

    @Test
    public void getCrocodilesUsingCookieAuth(){
        apiAuthentication.performLoginUsingCookieAuth();
        String cookie = apiAuthentication.getCookieValue("sessionid");
        myCrocodiles.getMyCrocodilesUsingCookieAuth(cookie);
        Assert.assertEquals(myCrocodiles.getStatusCode(),200);
    }

    @Test
    public void getCrocodilesUsingTokenAuth(){
        apiAuthentication.performLoginUsingTokenAuth();
        String token = apiAuthentication.getResponseBodyUsingKey("access");
        myCrocodiles.getMyCrocodilesUsingTokenAuth(token);
        Assert.assertEquals(myCrocodiles.getStatusCode(),200);
    }
}
