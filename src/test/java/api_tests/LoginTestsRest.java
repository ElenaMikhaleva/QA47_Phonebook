package api_tests;

import dto.ErrorMessageDto;
import dto.TokenDto;
import dto.User;
import io.restassured.response.Response;
import manager.AuthenticationController;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.BaseAPI;

import java.time.LocalDate;

public class LoginTestsRest extends AuthenticationController implements BaseAPI {
    SoftAssert softAssert = new SoftAssert();

    @Test
    public void loginPositiveTest() {
        User user = new User("elenam@gmail.com", "Password$1");
        Response response = requestRegLogin(user, LOGIN_URL);
        softAssert.assertEquals(response.getStatusCode(), 200);
        // сравниваем body
        System.out.println("=================================RESPONSE=================================================");
        System.out.println(response.getStatusLine());
        System.out.println("------------------------");
        System.out.println(response.getHeaders());
        System.out.println("------------------------");
        TokenDto tokenDto = response.body().as(TokenDto.class); // конвертировать body из JSON в Object
        System.out.println(tokenDto);
        System.out.println("===============================END RESPONSE==========================================");
        softAssert.assertTrue(tokenDto.toString().contains("token"));

        softAssert.assertAll();
    }

    @Test
    public void loginNegativeTest_invalidPassword() {
        User user = new User("elenam@gmail.com", "wrong");
        Response response = requestRegLogin(user, LOGIN_URL);
        softAssert.assertEquals(response.getStatusCode(), 401);
        // сравниваем body
        System.out.println("=================================RESPONSE=================================================");
        System.out.println(response.getStatusLine());
        System.out.println("------------------------");
        System.out.println(response.getHeaders());
        System.out.println("------------------------");
        ErrorMessageDto errorMessageDto = response.body().as(ErrorMessageDto.class);
        System.out.println(errorMessageDto);
        System.out.println("===============================END RESPONSE==========================================");

        softAssert.assertEquals(LocalDate.now().toString(), errorMessageDto.getTimestamp().substring(0, 10));
        softAssert.assertTrue(errorMessageDto.getError().equals("Unauthorized"));
        softAssert.assertTrue(errorMessageDto.getMessage().equals("Login or Password incorrect"));
        softAssert.assertTrue(errorMessageDto.getPath().contains("login/usernamepassword"));

        softAssert.assertAll();
    }

    @Test
    public void loginNegativeTest_invalidEmail() {
        User user = new User("elenam", "wrong");
        Response response = requestRegLogin(user, LOGIN_URL);
        softAssert.assertEquals(response.getStatusCode(), 401);
        // сравниваем body
        System.out.println("=================================RESPONSE=================================================");
        System.out.println(response.getStatusLine());
        System.out.println("------------------------");
        System.out.println(response.getHeaders());
        System.out.println("------------------------");
        ErrorMessageDto errorMessageDto = response.body().as(ErrorMessageDto.class);
        System.out.println(errorMessageDto);
        System.out.println("===============================END RESPONSE==========================================");
        System.out.println();

        softAssert.assertEquals(LocalDate.now().toString(), errorMessageDto.getTimestamp().substring(0, 10));
        softAssert.assertTrue(errorMessageDto.getError().equals("Unauthorized"));
        softAssert.assertTrue(errorMessageDto.getMessage().equals("Login or Password incorrect"));
        softAssert.assertTrue(errorMessageDto.getPath().contains("login/usernamepassword"));

        softAssert.assertAll();
    }
}
