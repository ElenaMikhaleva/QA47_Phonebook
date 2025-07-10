package manager;

import dto.Contact;
import dto.TokenDto;
import dto.User;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeSuite;
import utils.BaseAPI;

import static utils.PropertiesReader.*;

import static io.restassured.RestAssured.*;

public class ContactController implements BaseAPI {

    /* given()
        .header("Authorization", "value"
        .body(user)

        when()
            .post(url)
            .then() OR thenReturn() -> response
     */

    protected TokenDto tokenDto;

    @BeforeSuite
    public void login() {
        User user = new User(getProperty("login.properties", "email"), getProperty("login.properties", "password"));
        Response response = new AuthenticationController().requestRegLogin(user, LOGIN_URL);
        if (response.getStatusCode() == 200) {
            tokenDto = response.body().as(TokenDto.class);
        } else {
            System.out.println("Smth is wrong -> " + response.getStatusLine());
        }
    }

    protected Response addNewContactRequest(Contact contact) {
        return given()
                .body(contact)
                .baseUri(getProperty("login.properties", "baseUri"))
                .contentType(ContentType.JSON) // Content-Type: application/json
                .accept(ContentType.JSON)
                .header("Authorization", tokenDto.getToken())
                .post(ADD_NEW_CONTACT_URL)
                .thenReturn();
    }
}
