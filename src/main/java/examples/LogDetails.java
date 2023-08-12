package examples;

import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;

import java.io.PrintStream;

public class LogDetails {
    private final String baseUri = "https://reqres.in/";
    private final String usersEndpointPath = "api/users";
    private static final Logger logger = LogManager.getLogger(LogDetails.class);



    public LogDetails(){
        RestAssured.baseURI = baseUri;
    }

    public UsersPOJO convertResponseBodyToPOJO(){
        //RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        RestAssured.config = RestAssuredConfig.config()
                .logConfig(LogConfig.logConfig().enablePrettyPrinting(true));
        Response response = RestAssured.given().get(usersEndpointPath);
        response.then().log().body();
        UsersPOJO usersPOJO = response.as(UsersPOJO.class);
        //String logs = response.then().log().all().toString();
        //logger.info("Request and Response Details:\n{}", logs);
        // String logs = RestAssured.config().getLogConfig().defaultStream().toString();
        //System.out.println(logs);
        return usersPOJO;
    }
    public Response usersDetail(){
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        Response response = RestAssured.given().get(usersEndpointPath);
        String logs = response.then().log().all().toString();
        logger.info("Request and Response Details:\n{}", logs);
        return response;
    }
}
