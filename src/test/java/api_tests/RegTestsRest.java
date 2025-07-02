package api_tests;

import dto.User;
import io.restassured.response.Response;
import manager.AuthenticationController;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.BaseAPI;

import static utils.RandomUtils.*;

public class RegTestsRest extends AuthenticationController implements BaseAPI {

    @Test
    public void registration_PositiveTest_200() {
        User user = new User(generateEmail(10), "Password$123");
        Response response = requestRegLogin(user, REGISTRATION_URL);
        System.out.println(response.getStatusCode());
        System.out.println(response.getStatusLine());
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void registration_PositiveTest_getBody() {
        User user = new User(generateString(10), "Password$123");
        Response response = requestRegLogin(user, REGISTRATION_URL);
        System.out.println(response.getStatusCode());
        System.out.println(response.getStatusLine());
        System.out.println(response.getBody());
    }

    @Test
    public void registration_NegativeTest_400() {
        User user = new User(generateString(10), "Password$123");
        Response response = requestRegLogin(user, REGISTRATION_URL);
        System.out.println(response.getStatusCode());
        System.out.println(response.getStatusLine());
        Assert.assertEquals(response.getStatusCode(), 400);
    }
}
